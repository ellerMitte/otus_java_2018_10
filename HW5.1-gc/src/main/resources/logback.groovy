appender("FILE", FileAppender) {
    file = "HW5.1-gc/logs/gcBean.log"
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = "%date %level [%thread] %logger{10} [%file:%line] %msg%n"
    }
}

logger("ru.otus.GcDemo", DEBUG, ["FILE"])