package com.school.newsfeed.domain.schoolnewsdelivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.school.newsfeed.NewsFeedApplication;
import com.school.newsfeed.domain.school.Area;
import com.school.newsfeed.domain.school.SchoolType;
import com.school.newsfeed.domain.school.dto.MakeSchoolDto;
import com.school.newsfeed.domain.schoolnews.dto.MakeSchoolNewsDto;
import com.school.newsfeed.domain.user.UserType;
import com.school.newsfeed.domain.user.dto.LoginUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = NewsFeedApplication.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class SchoolNewDeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private MockHttpSession mockSession;
    private ObjectMapper mapper;
    private  final String managerEmail = "m2@test.com";
    private  final String studentEmail = "s1@test.com";
    //2개의 세션 권한을 번갈아 써야하기때문에 임의로 ID값 고정
    private  final String managerUUID = "e129560a-c6d1-43c7-8e58-ccce95e355b9";
    private  final String studentUUID = "fbc63b89-7110-432b-ac8a-e55be8c20afd";

    private String schoolId;
    public void setSessionAsSchoolManager(){
        mockSession=new MockHttpSession();
        mockSession.setAttribute("loginUser", new LoginUserDto(UUID.fromString(managerUUID),  managerEmail, "김관리자", UserType.SCHOOL_MANAGER));
    }

    public void setSessionAsStudent(){
        mockSession=new MockHttpSession();
        mockSession.setAttribute("loginUser", new LoginUserDto(UUID.fromString(studentUUID), studentEmail,"김학생",  UserType.STUDENT));
    }

    @Test
    public void contextLoad() throws Exception {
        mapper = new ObjectMapper();
        //given
        setSessionAsSchoolManager();// 1: 학교 관리자A 권한으로 학교 생성
        createSchoolByManager("뉴스피드초등학교");
        createSchoolNews(schoolId);//학교소식1 생성(구독한 학생이 없기때문에 이 학교소식은 my-news-feed에 표시 안됨)

        setSessionAsStudent();//2. 학생B 계정으로 구독함
        createSchoolSubscribes(schoolId);

        setSessionAsSchoolManager();// 3: 학교 관리자A 권한으로 돌아와서 학교소식2 생성
        createSchoolNews(schoolId);//(학생B는 이전 시점에 구독했기때문에 이 건에 대한 feed만 확인가능)

        //when
        setSessionAsStudent();
        //then
        getMyNewsFeed(); //적중 피드 1건
    }

    private void getMyNewsFeed()throws Exception{
        mockMvc.perform(get("/school-news-delivery/my-news-feed").session(mockSession))
                .andExpect(status().isOk())
                .andDo(document("schoolnewsdelivery-mynewsfeed",
                        responseFields(
                                fieldWithPath("[].deliveryId").description("피드배달 UUID"),
                                fieldWithPath("[].deliveryCreatedDate").description("피드배달 생성시각"),
                                fieldWithPath("[].schoolId").description("학교 UUID"),
                                fieldWithPath("[].schoolName").description("학교 이름"),
                                fieldWithPath("[].schoolNewsId").description("학교소식 UUID"),
                                fieldWithPath("[].schoolNewsTitle").description("학교소식 제목"),
                                fieldWithPath("[].schoolNewsContent").description("학교소식 본문"))));
    }
    private void createSchoolSubscribes(String schoolId) throws Exception{
        this.mockMvc
                .perform(post("/school-subscribe/school/{schoolId}", schoolId)
                        .queryParam("schoolId",schoolId).session(mockSession)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private void createSchoolByManager(String schoolName) throws Exception {
        MakeSchoolDto request = new MakeSchoolDto(schoolName, "세종특별자치시 소담1로 35", SchoolType.ELEMENTARY, Area.SEJONG);
        String jsonRequest = mapper.writeValueAsString(request);
        MvcResult response= this.mockMvc
                .perform(post("/school").session(mockSession)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        schoolId= JsonPath.parse(response.getResponse().getContentAsString()).read("id");
    }

    public void createSchoolNews(String schoolId) throws Exception{
        MakeSchoolNewsDto request = new MakeSchoolNewsDto(UUID.fromString(schoolId), "2024년 운영 안내", "2024년은 1학기와 2학기로 운영됩니다.(본문 내용 이어짐..)");
        String jsonRequest = mapper.writeValueAsString(request);
        this.mockMvc
                .perform(post("/school-news").session(mockSession)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}