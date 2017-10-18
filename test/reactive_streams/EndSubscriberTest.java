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
        // given
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        EndSubscriber<String> subscriber = new EndSubscriber<>();
        publisher.subscribe(subscriber);
        List<String> items = List.of("1", "x", "2", "x", "3", "x");

        // when
        assertThat(publisher.getNumberOfSubscribers()).isEqualTo(1);
        items.forEach(publisher::submit);
        publisher.close();

        // then
        await().atMost(1000, TimeUnit.MILLISECONDS).until(() -> {
            //subscriber의 consumedElements에 값이 세팅되지 않음.
            assertThat(subscriber.consumedElements).containsExactlyElementsOf(items);
        });
    }

}