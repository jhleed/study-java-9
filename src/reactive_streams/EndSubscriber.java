package reactive_streams;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;

public class EndSubscriber<T> implements Flow.Subscriber<T> {
    private Flow.Subscription subscription;
    public List<T> consumedElements = new LinkedList<>();

    /**
     * 지정된 Subscription에 대해 다른 Subscriber 메서드를 호출하기 전에 호출되는 메서드입니다.
     *
     * @param subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    /**
     * Subscription의 다음 항목으로 호출되는 메서드입니다.
     * @param item
     */
    @Override
    public void onNext(T item) {
        System.out.println("Got : " + item);
        consumedElements.add(item);
        subscription.request(1);
    }

    /**
     * Publisher 또는 Subscription에서 발생하는 복구 할 수없는 오류로 인해 호출 된 메서드입니다. 그 후에는 Subscription으로 다른 Subscriber 메서드가 호출되지 않습니다.
     *
     * Publisher가 Subscriber에게 항목을 발행하지 못하게하는 오류가 발생하면 해당 Subscriber는 onError를 수신 한 다음 더 이상 메시지를받지 않습니다.
     * @param t
     */
    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    /**
     * 아직 오류로 종료되지 않은 Subscription에 대해 추가 Subscriber 메서드 호출이 발생하지 않는 것으로 알려진 후 Subscription에서 다른 Subscriber 메서드를 호출하지 않는 경우 호출되는 메서드입니다.
     * 더 이상의 메시지가 발행되지 않을 것으로 알려진 경우, Subscriber는 onComplete를 실행합니다.
     */
    @Override
    public void onComplete() {
        System.out.println("Done");
    }
}
