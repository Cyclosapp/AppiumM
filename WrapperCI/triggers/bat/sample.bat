set projectLocation=E:\Bala\git-repo\Gerty\WrapperCI
cd %projectLocation%
set classpath=%projectLocation%\target\test-classes;%projectLocation%\lib\*
java org.testng.TestNG %projectLocation%\triggers\xml\cyclos_sprints.xml
pause