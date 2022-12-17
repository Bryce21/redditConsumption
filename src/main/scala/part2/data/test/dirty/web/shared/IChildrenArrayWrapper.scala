package part2.data.test.dirty.web.shared

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

/*
  Reddit json has a bunch of {children: []} properties.
  This is a reusable class to avoid a bunch of intermediate classes
 */
case class IChildrenArrayWrapper[T](children: List[T]) {}


object IChildrenArrayWrapper {
  implicit def decoder[T: Decoder]: Decoder[IChildrenArrayWrapper[T]] = deriveDecoder[IChildrenArrayWrapper[T]]
  implicit def encoder[T: Encoder]: Encoder[IChildrenArrayWrapper[T]] = deriveEncoder[IChildrenArrayWrapper[T]]
}


