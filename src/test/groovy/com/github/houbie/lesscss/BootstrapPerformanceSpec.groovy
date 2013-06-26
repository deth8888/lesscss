package com.github.houbie.lesscss

import ch.qos.logback.classic.Level
import org.slf4j.LoggerFactory
import spock.lang.Specification

class BootstrapPerformanceSpec extends Specification {
    static File source = new File('src/test/resources/less/bootstrap/bootstrap.less')
    static File destination = new File('build/tmp/bootstrap.css')
    static String expected = new File('src/test/resources/less/bootstrap/bootstrap.css').text

    def "compile twitter bootstrap less"() {

        long start = System.currentTimeMillis()

        when:
        LessCompiler compiler = new LessCompiler()
        LoggerFactory.getLogger(LessCompiler).level = Level.WARN

        then:
        (1..5).each {
            if (it > 1) {
                start = System.currentTimeMillis()
            }
            String result = compiler.compile(source, destination,
                    new Options(relativeUrls: false), new FileSystemResourceReader(source.getParentFile()), null)
            long time = System.currentTimeMillis() - start
            println("RHINO\t$it\t$time")
            result == expected
        }
    }
}