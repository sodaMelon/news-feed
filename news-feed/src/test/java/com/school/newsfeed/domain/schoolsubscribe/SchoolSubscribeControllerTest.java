package com.school.newsfeed.domain.schoolsubscribe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.school.newsfeed.NewsFeedApplication;
import com.school.newsfeed.domain.school.Area;
import com.school.newsfeed.domain.school.SchoolType;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = NewsFeedApplication.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class SchoolSubscribeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private MockHttpSession mockSession;
    private ObjectMapper mapper;

    private  final String managerEmail = "m3@test.com";
    private  final String studentEmail = "s3@test.com";

    private String schoolId;
    private String schoolSubscribeId;
    public void setSessionAsSchoolManager(){
        mockSession=new MockHttpSession();
        mockSession.setAttribute("loginUser", new LoginUserDto(UUID.randomUUID(),  managerEmail, "김관리자", UserType.SCHOOL_MANAGER));
    }

    public void setSessionAsStudent(){
        mockSession=new MockHttpSession();
        mockSession.setAttribute("loginUser", new LoginUserDto(UUID.randomUUID(),  studentEmail, "김학생", UserType.STUDENT));
    }

    @Test
    public void contextLoad() throws Exception {
        mapper = new ObjectMapper();
        setSessionAsSchoolManager();
        createSchoolByManager("제1구독초등학교");
        createSchoolByManager("제2구독초등학교");//매니저로 생성 시도하면 성공
        setSessionAsStudent();
        createSchoolSubscribes(schoolId);
      }

    private void createSchoolSubscribes(String schoolId) throws Exception{
        MvcResult response = this.mockMvc
                .perform(post("/school-subscribe/school/{schoolId}", schoolId)
                        .queryParam("schoolId",schoolId).session(mockSession)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("schoolsubscribe-create",
                        pathParameters(parameterWithName("schoolId").description("학교 UUID")),
                        responseFields(
                                fieldWithPath("id").description("학교구독 UUID"),
                                fieldWithPath("schoolId").description("학교 UUID"),
                                fieldWithPath("userId").description("유저 UUID"),
                                fieldWithPath("del").description("삭제 처리 여부"),
                                fieldWithPath("createdBy").description("생성 유저 UUID"),
                                fieldWithPath("modifiedBy").description("수정 유저 UUID"),
                                fieldWithPath("createdDate").description("생성 시각"),
                                fieldWithPath("modifiedDate").description("수정 시각")))).andReturn();
        schoolSubscribeId= JsonPath.parse(response.getResponse().getContentAsString()).read("id");
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
    
}