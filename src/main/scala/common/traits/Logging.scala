package common.traits

import org.slf4j.{Logger, LoggerFactory}

trait Logging[A] {
  protected lazy val logger: Logger = LoggerFactory.getLogger(getClass.getName)

}
