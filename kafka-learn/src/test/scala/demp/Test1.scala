package demp

import org.junit.jupiter.api.Test


/**
 * @desc:
 * @author guyu
 * @date 2022/9/18 21:26 
 */
class Test1 {
  var more = 10;
  @Test
  def bibaoTest():Unit={
    val one =  testBiBao()
    more = 20;
    val two =  testBiBao()

    print(one)

    print(two)



  }
  def testBiBao(): Int ={
    more*more
  }
}
