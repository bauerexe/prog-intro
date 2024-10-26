```
Testing Quote
    Testing em, strong, s, code, q
        Running test 01: java md2html.Md2Html "test1.in" "test1.out"
Exception in thread "main" java.lang.AssertionError: Line 1:
     expected `<h1>��������� ������� ������</h1>`,
       actual `<h1>��������� ������� ������`
        at base.Asserts.error(Asserts.java:75)
        at base.Asserts.assertTrue(Asserts.java:41)
        at base.Asserts.assertEquals(Asserts.java:20)
        at base.Runner.lambda$testEquals$0(Runner.java:35)
        at base.TestCounter.lambda$test$0(TestCounter.java:58)
        at base.TestCounter.lambda$testV$2(TestCounter.java:71)
        at base.Log.silentScope(Log.java:40)
        at base.TestCounter.testV(TestCounter.java:70)
        at base.TestCounter.test(TestCounter.java:57)
        at base.Runner.testEquals(Runner.java:29)
        at md2html.Md2HtmlTester$Checker.test(Md2HtmlTester.java:208)
        at md2html.Md2HtmlTester$Checker.test(Md2HtmlTester.java:193)
        at md2html.Md2HtmlTester.lambda$test$2(Md2HtmlTester.java:183)
        at base.Log.lambda$action$0(Log.java:18)
        at base.Log.silentScope(Log.java:40)
        at base.Log.scope(Log.java:31)
        at base.Log.scope(Log.java:24)
        at md2html.Md2HtmlTester.test(Md2HtmlTester.java:183)
        at base.Selector$Composite.lambda$v$0(Selector.java:134)
        at base.Selector.lambda$test$2(Selector.java:79)
        at base.Log.lambda$action$0(Log.java:18)
        at base.Log.silentScope(Log.java:40)
        at base.Log.scope(Log.java:31)
        at base.Log.scope(Log.java:24)
        at base.Selector.lambda$test$3(Selector.java:79)
        at java.base/java.lang.Iterable.forEach(Iterable.java:75)
        at base.Selector.test(Selector.java:79)
        at base.Selector.main(Selector.java:51)
        at md2html.FullMd2HtmlTest.main(FullMd2HtmlTest.java:120)
```
