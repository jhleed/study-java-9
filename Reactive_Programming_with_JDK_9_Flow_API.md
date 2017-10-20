# Reactive Programming with JDK 9 Flow API 번역

이 문서는 [오라클 커뮤니티 문서](https://community.oracle.com/docs/DOC-1006738)를 개인 공부 목적으로 일부 번역한 내용을 공유하는 것입니다. 오역이나 번역이 매끄럽지 않을 수 있습니다. 잘못된 부분이 있다면 피드백 바랍니다.
(하단의 `Processor` 파트는 아직 학습하지 않은 관계로 번역을 하지 않았습니다.)
Publisher를 게시자로 번역하였고, Subscriber를 구독자로 번역하였습니다. 중간에 용어가 종종 혼용됩니다.

### 리액티브 프로그래밍이란?

리액티브 프로그래밍은 데이터 항목의 비동기 스트림을 처리하는 것입니다. 여기서 애플리케이션은 발생하는 데이터 항목에 반응합니다. 데이터 스트림은 본질적으로 시간이 지남에 따라 발생하는 일련의 데이터 항목입니다. 이 모델은 메모리 내 데이터를 반복하는 것과 비교하여 데이터가 스트림으로 처리되기 때문에 **메모리를 더 효율적으로 처리합니다.**

리액티브 프로그래밍 모델에는 게시자(Publisher)와 구독자(Subscriber)가 있습니다. 게시자는 구독자가 비동기적으로 구독하는 데이터 스트림을 게시합니다.

이 모델은 또한 프로세서를 통해 스트림에서 작동하는 고차 함수를 도입하는 메커니즘을 제공합니다. 프로세서는 게시자 또는 구독자를 변경할 필요없이 데이터 스트림을 변환합니다. 프로세서 (또는 프로세서 체인)는 게시자와 구독자 사이에 위치하여 데이터의 한 스트림을 다른 스트림으로 변환합니다. 게시자와 구독자는 데이터 스트림에 발생하는 변환과 독립적입니다.

![Alt text](./1508327734408.png)

### 리액티브 프로그래밍의 장점

- Simpler code, making it more readable. (코드가 간단해서 가독성 상승)
- Abstracts away from boiler plate code to focus on business logic.
- Abstracts away from low-level threading, synchronization, and concurrency issues. (저수준 스레딩, 동기화 및 동시성 문제를 추상화)
- Stream processing implies memory efficient (스트림 처리는 메모리를 효율적으로 처리)
- The model can be applied almost everywhere to solve almost any kind of problem. (이 모델은 거의 모든 종류의 문제를 해결하기 위하여 거의 모든 곳에 적용 할 수 있습니다. (~~.....? 너무 믿지는 않는 것이 좋습니다.~~)

### JDK9의 Flow API

JDK 9의 Flow API는 실질적인 표준인 Reactive Streams Specification에 해당합니다. Reactive Streams Specification은 Reactive Programming을 표준화하기 위한 이니셔티브 중 하나입니다. 여러 구현체가 이미 Reactive Streams Specification을 지원합니다.

Flow API (및 Reactive Streams API)는 어떤 면에서 Iterator 및 Observer 패턴의 아이디어를 조합 한 것입니다. Iterator는 응용 프로그램이 소스에서 항목을 가져오는 풀 모델입니다. 옵저버는 푸시 모델로, 소스의 항목이 응용 프로그램으로 푸시됩니다. Flow API를 사용하여 응용 프로그램은 처음에 N 개의 항목을 요청한 다음 게시자는 N 개까지의 항목을 구독자에게 푸시합니다. 그래서 Pull과 Push 프로그래밍 모델이 혼합되어 있습니다.

![Alt text](./1508329956594.png)

### Flow API Interfaces

```
@FunctionalInterface   
public static interface Flow.Publisher<T> {  
    public void    subscribe(Flow.Subscriber<? super T> subscriber);  
}   
  
public static interface Flow.Subscriber<T> {  
    public void    onSubscribe(Flow.Subscription subscription);  
    public void    onNext(T item) ;  
    public void    onError(Throwable throwable) ;  
    public void    onComplete() ;  
}   
  
public static interface Flow.Subscription {  
    public void    request(long n);  
    public void    cancel() ;  
}   
  
public static interface Flow.Processor<T,R>  extends Flow.Subscriber<T>, Flow.Publisher<R> {  
}  
```

### Subscriber callback

구독자(Subscriber)는 콜백을 위해 게시자(Publisher)를 구독합니다.
데이터는 요청하지 않는 한 구독자에게 푸시되지 않습니다. 보통은 여러 항목들을 함께 요청합니다. 주어진 구독에 대한 구독자 메서드 호출은 엄격하게 명령받습니다. (Subscriber method invocations for a given Subscription are strictly ordered.)

애플리케이션은 구독자가 사용할 수 있는 아래의 콜백에 반응합니다.


1. **onSubscribe** : 지정된 Subscription(구독)에 대해 다른 Subscriber(구독자)메서드를 호출하기 전에 호출되는 메서드입니다.
2. **onNext** : Subscription의 다음 항목으로 호출되는 메서드입니다.
3. **onError** : Publisher(게시자) 또는 Subscription에서 발생하는 복구 할 수 없는 오류로 인해 호출된 메서드입니다. 그 후에는 Subscription으로 다른 Subscriber 메서드가 호출되지 않습니다. Publisher가 Subscriber에게 항목을 발행하지 못하게 하는 오류가 발생하면 해당 Subscriber는 onError를 수신한 다음 더 메시지를 받지 않습니다.
4. **onComplete** : 아직 오류로 종료되지 않은 Subscription에 대해 추가 Subscriber 메서드 호출이 발생하지 않는 것으로 알려진 후 Subscription에서 다른 Subscriber 메서드를 호출하지 않는 경우 호출되는 메서드입니다. 더 이상의 메시지가 발행되지 않을 것으로 알려진 경우, Subscriber는 onComplete를 실행합니다.

### Sample Subscriber

```
import java.util.concurrent.Flow.*;  
...  
  
public class MySubscriber<T> implements Subscriber<T> {  
  private Subscription subscription;  
  
  @Override  
  public void onSubscribe(Subscription subscription) {  
    this.subscription = subscription;  
    subscription.request(1); //a value of  Long.MAX_VALUE may be considered as effectively unbounded  
  }  
  
  @Override  
  public void onNext(T item) {  
    System.out.println("Got : " + item);  
    subscription.request(1); //a value of  Long.MAX_VALUE may be considered as effectively unbounded  
  }  
  
  @Override  
  public void onError(Throwable t) {  
    t.printStackTrace();  
  }  
  
  @Override  
  public void onComplete() {  
    System.out.println("Done");  
  }  
}  
```

### 게시자 (Publisher)
게시자는 등록 된 구독자에게 데이터 항목의 스트림을 게시합니다. 일반적으로 Executor를 사용하여 항목을 비동기 적으로 구독자에게 게시합니다. 
게시자는 각 구독에 대한 Subscriber 메서드 호출을 strictly하게 요청합니다.(Publishers ensure that Subscriber method invocations for each subscription are strictly ordered.)

아래는 JDK의 SubmissionPublisher를 사용하여 구독자에게 데이터 항목의 스트림을 게시하는 예시입니다.

```
import java.util.concurrent.SubmissionPublisher;  
...  
    //Create Publisher  
    SubmissionPublisher<String> publisher = new SubmissionPublisher<>();  
  
    //Register Subscriber  
    MySubscriber<String> subscriber = new MySubscriber<>();  
    publisher.subscribe(subscriber);  
  
    //Publish items  
    System.out.println("Publishing Items...");  
    String[] items = {"1", "x", "2", "x", "3", "x"};  
    Arrays.asList(items).stream().forEach(i -> publisher.submit(i));  
    publisher.close(); 
```

### 구독 (The Subscription)

Flow.Publisher와 Flow.Subscriber를 연결합니다. 구독자는 요청이있을 때만 항목을 수신하며 구독을 통해 언제든지 취소 할 수 있습니다.

**Subscription Method**

1. **request** : 지정된 수의 n 개 항목을 구독에 대한 현재 충족되지 않은 수요에 추가합니다.
2. **cancel** : 구독자가 메시지 수신을 중지합니다.
