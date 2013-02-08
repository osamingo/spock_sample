package spock.sample


import org.apache.commons.lang3.StringUtils

import spock.lang.Specification

/**
 * FacebookGraphUtils用にspockを使ってテスト
 *
 * @author tonomori_osamu
 *
 */
class FacebookGraphUtilsTest extends Specification {

	def "存在するユーザネームで試してみる"() {

		expect:"性別が判明しているユーザで正確に性別を取得できているかチェック"
		new FacebookGraphUtils().getGender(username).equals(gender)

		where:"存在し性別が判明しているアカウントでテスト"
		username	| gender
		'zuck'		| 'male'
		'sean'		| 'male'
		'sheryl'	| 'female'
	}

	def "ブランクのユーザネームを引数にしてみる"() {

		setup:"ブランクのユーザネームを用意"
		def username = " "

		when:"ブランクのユーザネームを引数に実行"
		new FacebookGraphUtils().getGender(username)

		then:"ユーザネームがブランクだと例外が投げられる"
		thrown(IllegalArgumentException)

		and : "改めてusernameが本当にブランクなのかチェック"
		StringUtils.isBlank(username)
	}
}
