package com.school.newsfeed.domain.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.newsfeed.NewsFeedApplication;
import com.school.newsfeed.domain.school.dto.MakeSchoolDto;
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

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = NewsFeedApplication.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class SchoolControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private MockHttpSession mockSession;
    private ObjectMapper mapper;
    private  final String managerEmail = "m2@test.com";
    private  final String studentEmail = "s1@test.com";

    public void setSessionAsSchoolManager(){
        mockSession=new MockHttpSession();
        mockSession.setAttribute("loginUser", new LoginUserDto(UUID.randomUUID(),  managerEmail, "김관리자", UserType.SCHOOL_MANAGER));
    }

    public void setSessionAsStudent(){
        mockSession=new MockHttpSession();
        mockSession.setAttribute("loginUser", new LoginUserDto(UUID.randomUUID(), studentEmail,"김학생",  UserType.STUDENT));
    }
    
    @Test
    public void contextLoad() throws Exception {
        mapper = new ObjectMapper();
        String schoolName = "소담초등학교";
        //searchBySchoolName(schoolName); (학교 이름 검색에서 해당하는 학교가 없으면) 신규학교 페이지를 생성한다.(구현 예정)
        setSessionAsStudent();
        createSchoolByStudent(schoolName);//학생으로 생성 시도하면 실패
        setSessionAsSchoolManager();
        createSchoolByManager(schoolName);//매니저로 생성 시도하면 성공
    }

    public void createSchoolByManager(String schoolName) throws Exception {
        MakeSchoolDto request = new MakeSchoolDto(schoolName, "세종특별자치시 소담1로 35",SchoolType.ELEMENTARY, Area.SEJONG);
        String jsonRequest = mapper.writeValueAsString(request);
        this.mockMvc
                .perform(post("/school").session(mockSession)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("school-create",
                        requestFields(
                                fieldWithPath("name").description("학교 이름"),
                                fieldWithPath("address").description("학교 주소"),
                                fieldWithPath("schoolType").description("학교 유형 ELEMENTARY | MIDDLE | HIGH"),
                                fieldWithPath("area").description("지역 SEOUL | SEJONG | BUSAN")),
                        responseFields(
                                fieldWithPath("id").description("학교 UUID"),
                                fieldWithPath("name").description("학교 이름"),
                                fieldWithPath("address").description("학교 주소"),
                                fieldWithPath("schoolType").description("학교 유형 ELEMENTARY | MIDDLE | HIGH"),
                                fieldWithPath("area").description("지역 SEOUL | SEJONG | BUSAN"),
                                fieldWithPath("del").description("삭제 처리 여부"),
                                fieldWithPath("createdBy").description("생성 유저 UUID"),
                                fieldWithPath("modifiedBy").description("수정 유저 UUID"),
                                fieldWithPath("createdDate").description("생성 시각"),
                                fieldWithPath("modifiedDate").description("수정 시각"))));
    }

    public void createSchoolByStudent(String schoolName) throws Exception {
        MakeSchoolDto request = new MakeSchoolDto(schoolName, "세종특별자치시 소담1로 35",SchoolType.ELEMENTARY, Area.SEJONG);
        String jsonRequest = mapper.writeValueAsString(request);
        this.mockMvc
                .perform(post("/school").session(mockSession)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(document("school-create-by-student"));
}
}