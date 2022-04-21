h2/bin/h2.sh 실행시 permission 거부 문제가 발생한 경우
% ./h2.sh
permission denied: ./h2.sh
chmod 755 h2.sh

introduce local variable
option+command+V

h2 데이터베이스의 test db가 제대로 생성되지 않는 경우
[Database &#34;/Users/asdfiop1517/test&#34; not found, either pre-create it or allow remote database creation (not recommended in secure environments) [90149-200]](http://192.168.1.18:8082/login.do?jsessionid=73ad5a05de4139fdd14b38c69c51afb8#) 90149/90149** **[(도움말)](https://h2database.com/javadoc/org/h2/api/ErrorCode.html#c90149)
localhost:8082 콘솔에서 연결을 눌러 접속하려고 할때 위 메세지 출력후 콘솔 접속이 안됨

persistence.xml 파일에서 javax.persistence.jdbc.url 값을 아래와 같이 설정후 서비스 시작하면 test.mv.db 파일이 생성됨.

```
<property name="javax.persistence.jdbc.url" value="jdbc:h2:~/test"/>
```

이후 설정을 다시 아래와 같이 설정하고 시작

```
<property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
```


영속성 컨텍스트
엔티티를 영구 저장하는 환경 이라는 뜻
EntityManager.persist(entity);

디비에 저장하는 것이 아니라 entity를 영속성 컨텍스트라는 곳에 저장한다는 의미

영속성 컨텍스트는 논리적인 개념
눈에 보이지 않음
엔티티 매니저를 통해서 영속성 컨텍스트에 접근
=> 엔티티매니저 : 영속성 컨텍스트 (1:1)


엔티티의 생명주기
비영속(new/transient) -> 영속(managed) -> 준영속(detached) -> 삭제(removed)

비영속
객체를 생성하였지만 영속성 컨텍스트에 집어 넣지 않은 상태

영속
.persist(object)
객체가 영속성 컨텍스트에 넣어진 상태
디비에 저장되는 상태는 아니다.
영속 상태가 된다고 해서 디비에 저장되는 것은 아니다
트랜잭션 매니저가 커밋을 실행하는 시점에 저장됨.

준영속
detached
객체가 영속성 컨텍스트에서 분리된 상태

삭제
removed
데이터베이스에서 삭제된 상태

영속성 컨텍스트의 이점
1차 캐시
동일성(identity) 보장
트랜잭션 지원하는 쓰기 지연 (transactional write-behind)
변경감지(Dirty Checking)
지연로딩(Lazy Loading)

1차 캐시
.persist(member)
PK로 설정한 값을 기준으로 객체가 저장되어 있음
em.find()를 수행하면 영속성 컨텍스트 안에서 엔티티를 조회하여 가져옴
1차 캐시에 없는 엔티티(예를 들어 member2)는 디비에서 조회하여 영속성 컨텍스트에 1차 캐시에 저장한 이후에 반환
이후에 member2를 조회하면 영속성 컨텍스트 안에서 조회하여 가져옴
