@echo off
cls
echo BUILDING YOUR FIGHTER...
rmdir /S /Q target
mkdir target\classes
javac -d target/classes -encoding utf8 -sourcepath src/main/java src/main/java/lt/visma/codefights/sdk/*.java src/main/java/lt/visma/codefights/sdk/boilerplate/*.java src/main/java/lt/visma/codefights/sdk/model/*.java src/main/java/lt/visma/codefights/sdk/samples/*.java
jar cvfe target/my-fighter.jar lt.visma.codefights.sdk.boilerplate.SDK -C target/classes lt/visma/codefights/ 
echo IF EVERYTHING WENT WELL, SPAR WITH YOUR FIGHTER BY RUNNING test-my-fighter-bat