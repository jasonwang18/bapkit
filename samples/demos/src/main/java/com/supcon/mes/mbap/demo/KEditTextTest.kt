package com.supcon.mes.mbap.demo

import com.supcon.common.view.base.activity.BasePresenterActivity
import com.supcon.common.view.util.LogUtil
import com.supcon.mes.mbap.view.setKeyName
import kotlinx.android.synthetic.main.ac_kotlin_test.*

class KEditTextTest : BasePresenterActivity(){

    override fun getLayoutID(): Int {
        return R.layout.ac_kotlin_test
    }


    override fun initView() {
        super.initView()

        verticalEdit.input = "水电费公司的反馈上岛咖啡水电费广东省分公什么什么什么模式买什么什么什么什么模式买什么什么什么模式没事没事司律地方规定发给对方更多师代理费是的风格的风格的发个梵蒂冈的手动方式独领风骚的浪费乐山大佛历史地理酸辣粉乐山大佛律师代理费收到了"
        verticalEdit.setKeyName("Kotlin")


        verticalText.value = "纵向文本测试"
        verticalText.setKeyName("Kotlin")

        val editText :String? = null
        LogUtil.d(""+editText.toString())


        verticalDate.date = "35345"

    }


}