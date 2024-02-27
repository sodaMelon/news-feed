# news-feed
school-news-feed


### 1. 시스템 개요 : 
학교 소식을 전달하고 받아보는 **'학교소식 뉴스피드'**

*(details & 요구사항 정리: [#1](https://github.com/sodaMelon/news-feed/issues/1) )*
* Backend : Java(ver.21), Spring boot, JPA(ORM) & QureyDsl
* DB : MySQL(개발 DB), H2(자동화된 테스트 진행 시 사용되는 휘발성 DB)
* Test : JUnit5, Spring REST DOCS, Jacoco(현재 **Code Coverage 90% 제한 有**)



### 2. 실행방법
~~1.~~ (현재는 휘발성 DB를 기본으로 동작하도록함) (필요 시 [commit#2364d87의 comment](https://github.com/sodaMelon/news-feed/commit/2364d8736271786e1627a6b8f393624226812a51)를 확인하여 본인이 사용할 DB를 연결한다.)
1) gradle을 통해 import > build > bootRun 과정을 거쳐 실행한다.
2) baseUrl의 HomePath(예:http://localhost:5000/) 로 접근하면, build 시 **테스트 자동화**로 생성된 API문서를 확인할 수 있다. [캡쳐본.pdf](https://github.com/sodaMelon/news-feed)

### 3. db & entity info 
*(details & 성능 issue 정리 : [#17](https://github.com/sodaMelon/news-feed/issues/17) , (automated by dbdocs.io))*

![erd](https://github.com/sodaMelon/news-feed/assets/66295123/30e63f45-919d-4739-b429-97122784b2fe)

### 4. Code Coverage Report : 
![code-coverage](https://github.com/sodaMelon/news-feed/assets/66295123/21c82421-6eee-4468-be8e-4189dc0da30a)



 ## QUICK CONTACT : sodamlee315@gmail.com
when you wanna do something, plz send email to me😇!! or Make GitHub-issues in this repository (I'll check it ASAP)
