package reactive_streams;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

public class EndSubscriberTest {
    @Test
    public void whenSubscribeToIt_thenShouldConsumeAll() throws InterruptedException {
        // given - 게시자, 구독자 설정
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        EndSubscriber<String> subscriber = new EndSubscriber<>();
        publisher.subscribe(subscriber);
        List<String> items = List.of("1", "x", "2", "x", "3", "x");

        // when 게시자가 구독자에게 데이터 스트림 전송 (submit)
        assertThat(publisher.getNumberOfSubscribers()).isEqualTo(1);
        items.forEach(publisher::submit); //구독자에게 처리를 맡기고 기다린다!
        publisher.close();

        // then - (대기 시간을 1초로 제한)
        // 구독자의 onNext 메서드가 실행된 것을 확인한다.
        await().atMost(1000, TimeUnit.MILLISECONDS).until(() -> assertThat(subscriber.consumedElements).containsExactlyElementsOf(items));
    }

}