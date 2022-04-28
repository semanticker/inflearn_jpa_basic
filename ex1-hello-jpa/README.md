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


플러시
영속석 컨텍스트의 변경 내용을 데이터베이스에 반영
영속성 컨테스트의 상황과 데이터베이스를 맞춰줌

플러시 발생
dirty checking
수정된 엔티티를 쓰기지연 SQL 저장소에 등록
쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송(등록, 수정, 삭제 쿼리)

영속성 컨텍스 플러시 방법
em.flush() - 직접 호출할 일은 없지만 테스트를 위해서 숙지
1차 캐시는 유지됨.
트랜잭션 커밋 - 플러시 자동 호출
JPQL 쿼리 실행 - 플러시 자동 호출
JPQL은 어떠한 쿼리든 실행하면 바로 플러시 처리됨.

플러시 모드 옵션
em.setFlushMode(FlushModeType.COMMIT)
FlushModeType.AUTO
기본값. 커밋이나 쿼리를 실행할때 플러시
FlushModeType.COMMIT
커밋할때만 플러시

플러시는
영속성 컨텍스트를 비우지 않음
영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 하면 됨
동시성은 데이터베이스 트랜잭션에 위임해서 사용함.

준영속 상태
영속 -> 준영속
영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
영속성 컨텍스트가 제공하는 기능(Dirty checking 등)을 사용 못함
em.detach(entity)
특정 엔티티만 준영속 상태로 전환
em.clear()
영속성 컨텍스트를 완전히 초기화
em.close()
영속성 컨텍스트 종료

객채와 테이블 매핑

앤티티 매핑
객체와 테이블 매핑 : @Entity, @Table
필드와 컬럼 매핑: @Column
기본 키 매핑 : @Id
연관관계 매핑: @ManyToOne, @JoinColumn

@Entity
@Entity가 붙은 클래스는 JPA가 관리, 엔티티
JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
주의

- 기본 생성자 필수
  파라메터가 없는 public 또는 protected 생성자
- final 클래스, enum, interface, inner 클래스 사용X
- 저장할 필드에 final 사용하면 안됨

속성: name

- JPA에서 사용할 엔티티 이름을 지정한다
- 기본값: 클래스 이름을 그대로 사용(예: Member)
  같은 클래스 이름이 없으면 가급적 기본값을 사용

# 데이터베이스 스키마 자동 생성

- 애플리케이션 실행할때 DDL을 자동 생성
- 테이블 중심에서 객체 중심으로 설계 가능
- DB Dialect을 활용해서 DB에 맞는 적절한 DDL 생성
- **생성된 DDL은 개발장비에서만 사용**
- 생성된 DDL은 운영서버에서는 사용하지 않거나 다듬은 후에 사용


### 속성

- hibernate.hbm2ddl.auto
- create - 기존 테이블 삭제 후 다시 생성(Drop + Create)
- create-drop - create와 같으나 종료시점에 테이블 DROP
- update - 변경분만 반영(운영DB에는 사용하면 안됨)
  - alter table과 같은 구조적 변경분만 반영.
  - ex) 테이블을 삭제하지 않고 컬럼을 추가함
  - 엔티티에서 필드를 삭제하면 alter table drop column 하지 않음.
- validate - 엔티티 테이블이 정상 매핑되었는지만 확인
  - 엔티티에 새로운 필드를 추가하면 테이블과 엔티티가 일치하는가만 확인함
  - 일치하지 않으면 에러가 발생함.
- none - 사용하지 않음
  - 속성을 주석처리하거나 none으로 설정하면 됨.
  - 실제로 지정된 값이 아님. 의미없는 값이나 관례상 적는 값임.

### 주의

- 운영장비에는 절대 create, create-drop, update 사용하면 안됨
- 개발초기 create 또는 update
- 테스트 서버에는 update 또는 validate
- 스테이징과 운영서버는 validate 또는 none

### DDL 생성 기능

- 제약조건 추가 : 회원이름 필수, 10자 초과 하면 안됨
  - @Column(unique = false, length = 10)
    - age integer not null
    - alter table MBR add constraint UK_4k9i8f9md75l3hth534jg2l2f unique (name)
- DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다.


## 필드와 컬럼 매핑


### 매핑 어노테이션 정리

#### hibernate.hbmddl.auto

- @Column - 컬럼매핑
- @Temporal - 날짜 타입 매핑
- @Enumerated - enum 타입 매핑
- @Lob - BLOB, CLOB 매핑
- @Transient - 특정 필드를 컬럼매핑 안함

### @Column

- name - 필드와 매핑할 테이블의 컬럼 이름
- insertable - 등록 가능 여부
- updatable - 수정 가능 여부
- nullable - null 값 허용 여부. DDL
- unique - 유니크 제약조건을 걸때 사용. DDL
- columnDefinition - 컬럼 정보를 직접 기술. varchar(100) default 'EMPTY'
- length - 문자 길이 제약 조건. String 타입만 사용. DDL
- precision, scale - BigDecimal 타입에 사용. 소수점을 포함한 전체 자릿수.
  scale은 소수의 자릿수. double, float 타입에는 적용되지 않음.

### @Enumerated

#### 자바 enum 타입을 매핑. ORDINAL 사용은 자제

#### value

- EnumType.ORDINAL : enum 순서를 데이터베이스에 저장 (기본값)
- EnumType.STRING : enum 이름을 데이터베이스에 저장


### @Temproal

#### 날짜타입(java.util.Date, java.util.Calendar)를 매핑

자바의 LocalDate 타입은 데이터베이스의 date

자바의 LodalDateTime 타입은 데이터베이스의 timestamp

#### value

- TemporalType.DATE - 날짜, 데이터베이싀 date 타입과 매핑 2020-01-01
- TemporalType.TIME - 시간, 데이터베이스 time 타입과 매핑 11:12:11
- TemporalType.TIMESTAMP - 날짜와 시간, 데이터베이스 timestamp 타입과 매핑


### @Lob

#### 데이터베이스 BLOB, CLOB 타입과 매핑

- @Lob에는 지정할 속성이 없음
- 매핑 필드 타입이 문자면 CLOB, 아니면 BLOB 매핑
  - CLOB - String, char[], java.sql.CLOB
  - BLOB - byte[], java.sql.BLOB

### @Transient

필드매핑, 데이테이베이스 조회, 저장 안함

주로 메모리상에서만 임시로 값을 보관
