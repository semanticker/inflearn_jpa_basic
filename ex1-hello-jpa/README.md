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
