# Spring 5 & Java 9 스터디 회고록

일시 : 2017.10.19 수요일

장소 : 신림 카페 파사디나

인원 : [최범균](https://www.facebook.com/beomkyun.choi?fref=gs&hc_ref=ARS4_4dS5IAaPFJb02kzF0OZBHgUA_CiGomN4o5XiG39ufKkwpyug3wubgteV732uIo&dti=767987643241037&hc_location=group), [전경훈](https://github.com/penpen787), [최강용](https://github.com/arahansa), [이종호](https://www.facebook.com/jhleed)

## 방식

Spring 5와 Java 9 에 대한 내용을 학습 & 코딩 후 공유 (룰이 별도로 없었음)

## 무엇을 배웠나요?

`jshell`, `interface priavte methods`, `jigsaw`, `Flow API (Publisher & Subscriber)`, `Factory Methods for Immutable Collections` 등등…. 완전 꼼꼼하게는 아니지만 **Java 9에서 변경된 것들이 대략 무엇인지 감을 잡을 수 있었다.** Spring 5 관련해서는 백그라운드가 부족한 관계로 이해하는 데 조금 어려움이 있었다. 특히 설정 부분이 조금 어려운 면이 있는 것 같았다. 이 부분은 12월쯤 **Spring 5가 적용될 Spring Boot에서 설정 간소화를 기대해 보기로 한다.**

잘하시는 분들 사이에서 **나는 어떻게 하면 다른 분들에게 도움이 될 수 있을지 고민**을 하다가 [내가 공부하며 번역한 Reactive Streams](http://jhleed.tistory.com/99) 를 공유하기로 하였다.

삽질을 조금 했던 부분은 [Baeldung 블로그](http://www.baeldung.com/java-9-reactive-streams)를 참고하여 Publisher와 Subscriber 예제를 만들었는데 그대로 타이핑했는데도 테스트 코드가 통과하지 않아서 여러 가지로 삽질을 하다 보니 해당 예제의 테스트 코드가 잘못되어 있는 것을 확인하고 수정하였다. 삽질의 과정에서 더 많이 배운 것 같기도 하다. (다른 부분은 못 보게 되었지만..)

## 회고

### 좋았던 점

Java 9, Spring 5와 같은 비교적 신기술은 아무래도 신빙성 있는 자료도 적고, 레퍼런스가 적어서 혼자 공부하는 것이 상대적으로 어렵다. (가장 신빙성 있는 자료인 공식 문서와 API 문서를 번역해가며 학습을 해야 한다.)

4명 정도 모여서 각자 다른 분야를 학습하여 공유하다 보니 모르는 내용을 질문도 할 수 있고, 거기에 대답하는 과정에서도 배울 게 있었다.

특히 **남에게 설명해줘야 한다는 마음으로 공부를 하였기 때문에 높은 집중도**로 학습을 할 수 있었던 것 같다.

### 다음에는 이렇게 해보는 것도 좋을 것 같다.

각자 어느 부분을 맡을 것인지 미리 공유를 해두는 것도 좋을 것 같다.

- ex : A는 Jigsaw, jshell을, B는 Reactive Streams를 , C, D는 WebFlux를 공부하겠다고 사전에 공유

그러면 중복되는 부분을 공부하지 않고 최대한 다양한 범위를 훑어볼 수 있지 않을까?
