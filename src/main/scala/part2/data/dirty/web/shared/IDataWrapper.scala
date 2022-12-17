package part2.data.dirty.web.shared

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

/*
  The reddit json has a bunch of {data: {}} properties
  This is a reusable class to avoid having a bunch of intermediate ones
 */
case class IDataWrapper[T](data: T)


object IDataWrapper {
  implicit def decoder[T: Decoder]: Decoder[IDataWrapper[T]] = deriveDecoder[IDataWrapper[T]]
  implicit def encoder[T: Encoder]: Encoder[IDataWrapper[T]] = deriveEncoder[IDataWrapper[T]]
}
