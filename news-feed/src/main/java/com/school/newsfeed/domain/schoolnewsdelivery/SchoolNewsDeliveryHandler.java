package com.school.newsfeed.domain.schoolnewsdelivery;

import com.school.newsfeed.domain.schoolnewsdelivery.repository.SchoolNewsDeliveryRepository;
import com.school.newsfeed.domain.schoolsubscribe.SchoolSubscribe;
import com.school.newsfeed.domain.schoolsubscribe.SchoolSubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SchoolNewsDeliveryHandler {
    private final SchoolSubscribeRepository subscribeRepository;
    private final SchoolNewsDeliveryRepository deliveryRepository;

    /**
     * 학교_소식 생성 후, 실제 구독자들에게 학교_소식_배송 (구독자가 늘어날 수록 무거워지기때문에 비동기로 실행 검토 필요 )
     */
    @EventListener
    public void eventHandler(SchoolNewsDelivery deliveryEvent){
        List<SchoolSubscribe> activeSubscribes = subscribeRepository.findAllBySchoolIdAndDel(deliveryEvent.getSchoolId(),false);
        List<SchoolNewsDelivery> realDeliveries = new ArrayList<>();
        UUID newsId = deliveryEvent.getSchoolNewsId();
        activeSubscribes.forEach(s-> realDeliveries.add(new SchoolNewsDelivery(s, newsId)));
        deliveryRepository.saveAll(realDeliveries);
    }
}
