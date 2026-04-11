#  게시글 기능 정리

## 1. 필요 기능
- 게시글 목록 조회
- 게시글 상세 조회
- 게시글 작성
- 게시글 수정
- 게시글 삭제


## 2. 저장해야 하는 데이터 (Post)
- postId : 각 게시글을 구분하는 고유 식별자
- title : 게시글 제목 (최대 50자)
- content : 게시글 본문 내용
- author : 작성자 정보
- createdAt : 작성 시간
- likeCount : 공감 수
- commentCount : 댓글 수
- isAnonymous : 익명 여부


## 3. 요청 데이터

### 게시글 목록 조회
- 요청 예시: 게시판 진입
- 서버가 받아야 할 것: 게시판 종류(필요한 경우)

### 게시글 상세 조회
- postId

### 게시글 작성
- title
- content
- isAnonymous

### 게시글 수정
- postId
- newTitle
- newContent

### 게시글 삭제
- postId


## 4. 응답 데이터

### 게시글 목록 조회
- postId
- title
- contentPreview
- author
- createdAt
- commentCount
- likeCount

### 게시글 상세 조회
- postId
- title
- content
- author
- createdAt
- commentCount
- likeCount

### 게시글 작성
- postId
- message

### 게시글 수정
- message

### 게시글 삭제
- message


## 5. 검증해야 하는 조건

### 게시글 작성
- title은 필수값
- title은 50자 이하
- content는 필수값
- content는 공백 불가

### 게시글 조회 / 수정 / 삭제
- 해당 postId에 해당하는 게시글이 존재해야 함
- 존재하지 않을 경우 예외 처리 필요

### 공통
- 익명 게시글일 경우 작성자는 익명으로 처리