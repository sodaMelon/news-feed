package com.school.newsfeed.domain.schoolnews;

import com.school.newsfeed.domain.schoolmanager.SchoolManager;
import com.school.newsfeed.domain.schoolmanager.SchoolMangerRepository;
import com.school.newsfeed.domain.schoolnews.dto.MakeSchoolNewsDto;
import com.school.newsfeed.domain.user.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolNewService {
    private final SchoolMangerRepository schoolManagerRepository;
    private final SchoolNewsRepository schoolNewsRepository;
    public SchoolNews makeNewNews(MakeSchoolNewsDto dto, LoginUserDto user) {
       List<SchoolManager> managers = schoolManagerRepository.findAllBySchoolIdAndDel(dto.getSchoolId(),false);
       boolean isExist = false;
       SchoolNews result = null;
       for (SchoolManager s : managers){
        if(s.getUserId().equals(user.getUserId())){
            isExist=true;
            break;
        }
       }
       if (isExist) {
           return schoolNewsRepository.save(new SchoolNews(dto)); //todo 학교 구독자들에게 SchoolNewsHistory발행
       }
        return result;
    }
}
