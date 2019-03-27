import java.io.{FileWriter, PrintWriter}
import scala.io.Source

object Main {

  val brands = Array("Asus", "Bgh", "Blackberry", "Blu", "Caterpillar", "Cx", "Go", "Hyundai", "Htc", "Huawei", "Iphone", "Kanji", "K10",
                     "Ken", "Lg", "Maxwest", "Moto", "Motorola", "Nextel", "Nokia", "Panacom", "Philco", "Samsung", "Tcl", "Ulefone",
                     "Xiaomi", "Zte")


  def main(args: Array[String]): Unit = {

    val dataPhones = Source.fromFile("cellphoneslisting.csv").getLines().drop(1)

      .map(_.split("\u0001"))
      .map { x =>
        val v1 = x(0)
        val v2 = {
          brands.indexWhere(p => x(1).contains(p))
        }
        val v3 = x(2).toDouble
        val v4 = x(3).toLong
        if (v2 != -1)
          (brands(v2), v3, v4)
        else
          ("Otros", v3, v4)
      } toList

    val listPhones = dataPhones.groupBy(_._1).map {
      row => (row._1, row._2.map(_._2).reduce(_+_), row._2.map(_._3).reduce(_+_))
    }

    val list = new PrintWriter(new FileWriter("cellbrandslisting.csv"))
    list.println("Marca,Monto,Unidades")
    listPhones.foreach(p => (list.print(p._1 + ","), list.print(p._2 + ","), list.println(p._3)))
    list.close()
  }
}