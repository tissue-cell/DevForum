# dev_forum
 Spring Framework & React.js 기반 개발자 웹포럼 프로젝트
 
## 최초 세팅
1. 프로젝트/src/main 경로에서 [npm install --save-dev] 명령어로 package.json에 들어가있는 모듈을 모두 내려받는다.
2. 개발 진행시 프로젝트/src/main 경로에서 [npm run develop] 명령어로 webpack watch 기능을 활성화 한다.

## 프로젝트 구조
- src/main/front 디렉토리는 리액트 파일을 관리한다.
- src/main/webapp/resources 디렉토리는 webpack을 통한 패키징 파일과 이미지 리소스들이 포함된다.
- src/main/java 디렉토리는 자바 파일을 관리한다.
- src/main/webapp/WEB-INF/views 디렉토리는 jsp파일 뷰단을 관리한다.
- src/main/webapp/WEB-INF/spring 디렉토리는 context xml 파일들을 관리한다.
 
## 개발 환경
- java8
- Apache Tomcat 8.5
- Spring 4.3.29
- Mysql 5.7.36
- Mybatis 3.4.1
- Spring Secrutiy 4.1.3

## Commit Style

##메시지 구조
커밋 메시지는 제목(subject), 본문(body), 꼬리말(footer) 세가지 파트로 나누고 각 파트는 빈줄을 두어서 구분

```
type: subject

body(옵션)

footer(옵션)
```

### 타입

```
feat : 새로운 기능 추가
fix : 버그수정
docs : 문서 수정
style : (코드의 수정 없이) 스타일(style)만 변경(들여쓰기 같은 포맷이나 세미콜론을 빼먹은 경우)
refactor : 코드 리펙토링
test : test 관련 코드 추가, 수정(만약 기능추가와 테스트코드를 동시에 작성하였다면 feat으로 작성)
chore : (코드 수정없이) 설정변경
```

### 제목(subject)
- 제목은 항시 입력해야 하며 반드시 타입과 함께 작성되어야 합니다.
- ``type: [#issueNumber - ]subject 형태로 작성한다.``
    - 개인/팀의 스타일에 따라 type과 subject의 위치 또는 표현 방식(:대신 []로 감싼다 등등)은 마음껏 변경해도됩니다. 다만, 반드시 제목은 작성해야 하며, 제목에는 type과 subject가 함께 작성되어야 한다.
    - []안의 내용은 옵션이며 Github등 이슈 트래킹이 가능한 도구를 함께 쓴다면 #1234와 같이 이슈트래킹에서 제공하는 이슈 번호를 제목앞에 넣어주면 됩니다. ex) feat: #1234 - 회원가입 기능 추가
- 타입은 영문 소문자로 작성한다.
- 제목은 50자를 넘지않도록 주의한다.
- 제목은 해당 커밋에 대한 주요 내용을 간략하게 기록한다.
    

### 본문(body)
- 커밋에서 수정된 상세내역을 작성한다. 여기선 평서문으로 작성하면된다.
- 본문은 생략 가능, 제목(subject) 라인과 반드시 한줄 띄운다
- 수정 내역을 불릿기호(*)를 이용해서 하나씩 입력하여도 좋다.
- 본문의 내용은 어떻게 보다는 무엇을 왜에 맞춰서 작성한다.


### 꼬리말(footer)
- 꼬리말을 해당 커밋과 연관된 이슈 트래킹 번호를 입력한다.
- 제목에는 커밋이 온전히 한개의 이슈에 해당하는 경우에만 추가해서 사용하고 그 외의 경우 대부분 꼬리말에 이슈 번호를 라벨과 함께 추가한다.
- 꼬리말은 생략 가능하고 반드시 제목(subject) 또는 본문(body) 라인과는 반드시 한줄을 띄운다.

### 예제

```
feat: Summarize changes in around 50 characters or less

More detailed explanatory text, if necessary. Wrap it to about 72
characters or so. In some contexts, the first line is treated as the
subject of the commit and the rest of the text as the body. The
blank line separating the summary from the body is critical (unless
you omit the body entirely); various tools like `log`, `shortlog`
and `rebase` can get confused if you run the two together.

Explain the problem that this commit is solving. Focus on why you
are making this change as opposed to how (the code explains that).
Are there side effects or other unintuitive consequenses of this
change? Here's the place to explain them.

Further paragraphs come after blank lines.

 - Bullet points are okay, too

 - Typically a hyphen or asterisk is used for the bullet, preceded
   by a single space, with blank lines in between, but conventions
   vary here

If you use an issue tracker, put references to them at the bottom,
like this:

Resolves: #123
See also: #456, #789
```
