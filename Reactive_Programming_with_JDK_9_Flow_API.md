# Reactive Programming with JDK 9 Flow API

Oracle 공식 문서를 번역하였음

### 리액티브 프로그래밍이란?

리액티브 프로그래밍은 데이터 항목의 비동기 스트림을 처리하는 것입니다. 여기서 애플리케이션은 발생하는 데이터 항목에 반응합니다. 데이터 스트림은 본질적으로 시간이 지남에 따라 발생하는 일련의 데이터 항목입니다. 이 모델은 메모리 내 데이터를 반복하는 것과 비교하여 데이터가 스트림으로 처리되기 때문에 **메모리가 더 효율적입니다.**

리액티브 프로그래밍 모델에는 게시자(publisher)와 구독자(Subscriber)가 있습니다. 게시자는 구독자가 비동기적으로 구독하는 데이터 스트림을 게시합니다.

이 모델은 또한 프로세서를 통해 스트림에서 작동하는 고차 함수를 도입하는 메커니즘을 제공합니다. 프로세서는 게시자 또는 구독자를 변경할 필요없이 데이터 스트림을 변환합니다. 프로세서 (또는 일련의 프로세서)는 게시자와 구독자 사이에 위치하여 데이터의 한 스트림을 다른 스트림으로 변환합니다. 게시자와 구독자는 데이터 스트림에 발생하는 변환과 독립적입니다.

![Alt text](./1508327734408.png)

### 리액티브 프로그래밍의 장점

- Simpler code, making it more readable. (코드가 간단해서 가독성 상승)
- Abstracts away from boiler plate code to focus on business logic.
- Abstracts away from low-level threading, synchronization, and concurrency issues. (저수준 스레딩, 동기화 및 동시성 문제를 추상화)
- Stream processing implies memory efficient (스트림 처리는 메모리를 효율적으로 함축함)
- The model can be applied almost everywhere to solve almost any kind of problem.

### JDK9의 Flow API

JDK 9의 Flow API는 실질적인 표준인 Reactive Streams Specification에 해당합니다. Reactive Streams Specification은 Reactive Programming을 표준화하기위한 이니셔티브 중 하나입니다. 여러 구현은 이미 반응 흐름 사양을 지원합니다.

Flow API (및 Reactive Streams API)는 어떤면에서 Iterator 및 Observer 패턴의 아이디어를 조합 한 것입니다. Iterator는 응용 프로그램이 소스에서 항목을 가져 오는 풀 모델입니다. 옵저버는 푸시 모델로, 소스의 항목이 응용 프로그램으로 푸시됩니다. Flow API를 사용하여 응용 프로그램은 처음에 N 개의 항목을 요청한 다음 게시자는 N 개까지의 항목을 구독자에게 푸시합니다. 그래서 Pull과 Push 프로그래밍 모델이 혼합되어 있습니다.

![Alt text](./1508329956594.png)

### Subscriber callback

1. **onSubscribe** : 지정된 Subscription에 대해 다른 Subscriber 메서드를 호출하기 전에 호출되는 메서드입니다.
2. **onNext** : Subscription의 다음 항목으로 호출되는 메서드입니다.
3. **onError** : Publisher 또는 Subscription에서 발생하는 복구 할 수없는 오류로 인해 호출 된 메서드입니다. 그 후에는 Subscription으로 다른 Subscriber 메서드가 호출되지 않습니다. Publisher가 Subscriber에게 항목을 발행하지 못하게하는 오류가 발생하면 해당 Subscriber는 onError를 수신 한 다음 더 이상 메시지를받지 않습니다.
4. **onComplete** : 아직 오류로 종료되지 않은 Subscription에 대해 추가 Subscriber 메서드 호출이 발생하지 않는 것으로 알려진 후 Subscription에서 다른 Subscriber 메서드를 호출하지 않는 경우 호출되는 메서드입니다. 더 이상의 메시지가 발행되지 않을 것으로 알려진 경우, Subscriber는 onComplete를 실행합니다.

### Subscription Method

1. **request** : 지정된 수의 n 개 항목을 구독에 대한 현재 충족되지 않은 수요에 추가합니다.
2. **cancel** : 구독자가 (결국) 메시지 수신을 중지합니다.

### Reference

- [오라클 공식 문서](https://community.oracle.com/docs/DOC-1006738)