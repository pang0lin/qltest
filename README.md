# qltest

I create codeql database with ecj.jar, fllowing the steps

1) Change the path in file.txt

   `/Users/pang0lin/Downloads/qltest/org/apache/jsp/desktop_jsp.java
   /Users/pang0lin/Downloads/qltest/org/apache/jsp/keylog_jsp.java
   /Users/pang0lin/Downloads/qltest/org/apache/jsp/common_jsp.java
   /Users/pang0lin/Downloads/qltest/org/apache/jsp/errorMsg_jsp.java`

2) using the following command to create database

   `arch -x86_64 codeql database create out/database/OAapp --language=java --command="/bin/bash -c /Users/xxx/CodeQLpy/out/decode/run.sh" --overwrite`

the file "run.sh" is in the project
