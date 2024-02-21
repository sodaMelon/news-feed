package com.school.newsfeed.domain.schoolnews;

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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = NewsFeedApplication.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class SchoolNewsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private MockHttpSession mockSession;
    private ObjectMapper mapper;
    private  final String managerEmail = "m3@test.com";

    private String schoolId;
    private String schoolNewsId;

    public void setSessionAsSchoolManager(){
        mockSession=new MockHttpSession();
        mockSession.setAttribute("loginUser", new LoginUserDto(UUID.randomUUID(),  managerEmail, "김관리자", UserType.SCHOOL_MANAGER));
    }

    @Test
    public void contextLoad() throws Exception {
        mapper = new ObjectMapper();
        String schoolName = "소식초등학교";
        setSessionAsSchoolManager();
        createSchoolByManager(schoolName);//매니저로 생성 시도하면 성공
        createSchoolNews(schoolId);
        searchSchoolNews(schoolId);
        updateSchoolNews(schoolNewsId);
        deleteSchoolNews(schoolNewsId);
    }

    public void createSchoolNews(String schoolId) throws Exception{
        MakeSchoolNewsDto request = new MakeSchoolNewsDto(UUID.fromString(schoolId), "2024년 운영 안내", "2024년은 1학기와 2학기로 운영됩니다.(본문 내용 이어짐..)");
        String jsonRequest = mapper.writeValueAsString(request);
        MvcResult response = this.mockMvc
                .perform(post("/school-news").session(mockSession)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("schoolnews-create",
                        requestFields(
                                fieldWithPath("schoolId").description("학교 UUID"),
                                fieldWithPath("title").description("학교소식 본문"),
                                fieldWithPath("content").description("학교소식 본문")),
                        responseFields(
                                fieldWithPath("id").description("학교소식 UUID"),
                                fieldWithPath("schoolId").description("학교 UUID"),
                                fieldWithPath("title").description("학교소식 제목"),
                                fieldWithPath("content").description("학교소식 본문"),
                                fieldWithPath("del").description("삭제 처리 여부"),
                                fieldWithPath("createdBy").description("생성 유저 UUID"),
                                fieldWithPath("modifiedBy").description("수정 유저 UUID"),
                                fieldWithPath("createdDate").description("생성 시각"),
                                fieldWithPath("modifiedDate").description("수정 시각")))).andReturn();
       schoolNewsId = JsonPath.parse(response.getResponse().getContentAsString()).read("id");
    }

    public void searchSchoolNews(String schoolId) throws Exception{
        this.mockMvc
                .perform(get("/school-news/school/{schoolId}",schoolId).session(mockSession)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("schoolnews-search",
                        pathParameters(parameterWithName("schoolId").description("학교 ID"))
                        ,
                        responseFields(
                                fieldWithPath("[].id").description("학교소식 UUID"),
                                fieldWithPath("[].schoolId").description("학교 UUID"),
                                fieldWithPath("[].title").description("학교소식 제목"),
                                fieldWithPath("[].content").description("학교소식 본문"),
                                fieldWithPath("[].del").description("삭제 처리 여부"),
                                fieldWithPath("[].createdBy").description("생성 유저 UUID"),
                                fieldWithPath("[].modifiedBy").description("수정 유저 UUID"),
                                fieldWithPath("[].createdDate").description("생성 시각"),
                                fieldWithPath("[].modifiedDate").description("수정 시각"))));
    }

    public void updateSchoolNews(String schoolNewsId) throws Exception{
        MakeSchoolNewsDto request = new MakeSchoolNewsDto(UUID.fromString(schoolId), "2024년 수정 안내", "2024년은~ (수정된 본문 내용 이어짐..)");
        String jsonRequest = mapper.writeValueAsString(request);
        this.mockMvc
                .perform(patch("/school-news/{schoolNewsId}", schoolNewsId).session(mockSession)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("schoolnews-update", pathParameters(
                                parameterWithName("schoolNewsId").description("학교소식 UUID")
                        ),
                        requestFields(
                                fieldWithPath("schoolId").description("학교 UUID"),
                                fieldWithPath("title").description("학교소식 제목"),
                                fieldWithPath("content").description("학교소식 본문"))));
    }

    public void deleteSchoolNews(String schoolNewsId) throws Exception{
        this.mockMvc
                .perform(delete("/school-news/{schoolNewsId}", schoolNewsId)
                        .queryParam("schoolId",schoolId).session(mockSession)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("schoolnews-delete",
                        pathParameters(parameterWithName("schoolNewsId").description("학교소식 UUID")),
                        queryParameters(parameterWithName("schoolId").description("학교 UUID"))));
    }

    private void createSchoolByManager(String schoolName) throws Exception {
        MakeSchoolDto request = new MakeSchoolDto(schoolName, "세종특별자치시 소담1로 35", SchoolType.ELEMENTARY, Area.SEJONG);
        String jsonRequest = mapper.writeValueAsString(request);
        MvcResult response= this.mockMvc
                .perform(post("/school").session(mockSession)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        schoolId=JsonPath.parse(response.getResponse().getContentAsString()).read("id");
    }


}