package com.school.newsfeed.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.school.newsfeed.NewsFeedApplication;
import com.school.newsfeed.domain.user.dto.UserJoinRequest;
import com.school.newsfeed.domain.user.dto.UserJoinResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = NewsFeedApplication.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    private String mangerUserId;

    @Test
    public void contextLoad() throws Exception{
        String managerEmail = "manager@email.com";
        mapper= new ObjectMapper();
        createNewUser(managerEmail);
        createNewUserFail(managerEmail);
        loginFail("wrong@email.com");
        login(managerEmail);
        }

    public void createNewUser(String managerEmail) throws Exception {
        UserJoinRequest request = new UserJoinRequest(managerEmail, "김관리자", "010-9876-5432", UserType.SCHOOL_MANAGER);
        String jsonRequest = mapper.writeValueAsString(request);

        this.mockMvc
                .perform(post("/user/join")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-join",
                requestFields(
                        fieldWithPath("email").description("가입용 이메일"),
                        fieldWithPath("name").description("이름"),
                        fieldWithPath("phone").description("연락처"),
                        fieldWithPath("userType").description("유저타입(SCHOOL_MANAGER | STUDENT")),
                responseFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("existBefore").description("해당 메일로 가입한 적 있는지 YN"))
                ));
    }

    public void createNewUserFail(String managerEmail) throws Exception {
        UserJoinRequest request = new UserJoinRequest(managerEmail, "김중복", "010-9876-5432", UserType.SCHOOL_MANAGER);
        UserJoinResponse response = new UserJoinResponse(managerEmail, true);
        String jsonRequest = mapper.writeValueAsString(request);

        this.mockMvc
                .perform(post("/user/join")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(document("user-join-fail"
                        ));
    }

    public void login(String managerEmail) throws Exception {
        MvcResult response = mockMvc.perform(get("/user/login")
                        .param("email", managerEmail))
                .andExpect(status().isOk())
                .andDo(document("user-login",
                        queryParameters(parameterWithName("email").description("가입한 이메일")),
                        responseFields(
                                fieldWithPath("userId").description("사용자의 고유ID"),
                                fieldWithPath("email").description("사용자의 이메일"),
                                fieldWithPath("userName").description("사용자의 이름"),
                                fieldWithPath("userType").description("사용자 타입 (SCHOOL_MANAGER | STUDENT)")
                        )))
                .andReturn();
        mangerUserId = JsonPath.parse(response.getResponse().getContentAsString()).read("userId");
    }

    public void loginFail(String wrongEmail) throws Exception {
        mockMvc.perform(get("/user/login")
                        .param("email", wrongEmail))
                .andExpect(status().isBadRequest())
                .andDo(document("user-login-fail"));
    }

}
