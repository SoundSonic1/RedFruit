package com.example.redfruit.adapter

import com.example.redfruit.data.adapter.SubredditAboutAdapter
import com.example.redfruit.data.model.SubredditAbout
import com.squareup.moshi.Moshi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class SubredditAboutAdapterTest {

    private val moshi = Moshi.Builder().add(SubredditAboutAdapter()).build()
    private val adapter = moshi.adapter(SubredditAbout::class.java)

    @Test
    fun `valid subreddit about string`() {

        val sub = adapter.fromJson(validSubString) ?: fail("Sub must not be null.")

        assertEquals("linux", sub.display_name)

        assertEquals(true, sub.over18)

        assertEquals(441911, sub.subscribers)

        assertEquals("https://c.thumbs.redditmedia.com/pk674bZR_0PUDeDt.png", sub.header_img)

        assertEquals(
            "https://b.thumbs.redditmedia.com/3bTPnekMAM-6dMMkpNjaUh7DT74s9cv5Rg67hJNOhUs.png",
            sub.icon_img
        )

        assertTrue(sub.banner_background_image!!.isBlank())

        assertEquals("public", sub.subreddit_type)

        assertEquals(
            "All things Linux and GNU/Linux -- this is neither a community exclusively about the kernel Linux, nor is exclusively about the GNU operating system.",
            sub.public_description
        )
    }

    @Test
    fun `empty json`() {
        assertNull(adapter.fromJson("""{}"""))
    }

    @Test
    fun `wrong kind`() {
        assertNull(adapter.fromJson("""{"kind": "t3"}"""))
        assertNull(adapter.fromJson("""{"kind": null}"""))
    }

    @Test
    fun `empty data`() {
        assertNull(adapter.fromJson("""{"kind": "t5", "data": []}"""))
        assertNull(adapter.fromJson("""{"kind": "t5", "data": null}"""))
    }

    companion object {
        private const val validSubString =
            """{
  "kind": "t5",
  "data": {
    "user_flair_background_color": null,
    "submit_text_html": "&lt;!-- SC_OFF --&gt;&lt;div class=\"md\"&gt;&lt;h3&gt;Rules&lt;\/h3&gt;\n\n&lt;ul&gt;\n&lt;li&gt;Make sure your post is relevant to &lt;a href=\"\/r\/linux\"&gt;r\/linux&lt;\/a&gt;!&lt;\/li&gt;\n&lt;li&gt;This is &lt;strong&gt;not&lt;\/strong&gt; a support forum! Head to &lt;a href=\"\/r\/linuxquestions\"&gt;\/r\/linuxquestions&lt;\/a&gt;, &lt;a href=\"\/r\/linux4noobs\"&gt;\/r\/linux4noobs&lt;\/a&gt;, or other applicable subreddits for help. Looking for a distro? Try &lt;a href=\"\/r\/findmeadistro\"&gt;r\/findmeadistro&lt;\/a&gt;.&lt;\/li&gt;\n&lt;li&gt;Please submit the original article. Spamblog submissions are subject to removal and readers are encouraged to report them. See list of banned domains here: &lt;a href=\"\/r\/linux\/wiki\/settings\/rules\/banneddomains\"&gt;r\/linux\/wiki\/settings\/rules\/banneddomains&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;No memes, image macros or rage comics&lt;\/li&gt;\n&lt;li&gt;No trolling, harassing, or otherwise creating a poor discussion&lt;\/li&gt;\n&lt;li&gt;No links to sites that require a login (i.e. Facebook), URL shorteners, or otherwise misdirecting users&lt;\/li&gt;\n&lt;li&gt;No NSFW posts.&lt;\/li&gt;\n&lt;li&gt;Urgent spam filter requests: Message the mods and include link&lt;\/li&gt;\n&lt;\/ul&gt;\n&lt;\/div&gt;&lt;!-- SC_ON --&gt;",
    "restrict_posting": true,
    "user_is_banned": false,
    "free_form_reports": true,
    "wiki_enabled": true,
    "user_is_muted": false,
    "user_can_flair_in_sr": true,
    "display_name": "linux",
    "header_img": "https:\/\/c.thumbs.redditmedia.com\/pk674bZR_0PUDeDt.png",
    "title": "Linux, GNU\/Linux, free software...",
    "icon_size": [
      256,
      256
    ],
    "primary_color": "#282a36",
    "active_user_count": 904,
    "icon_img": "https:\/\/b.thumbs.redditmedia.com\/3bTPnekMAM-6dMMkpNjaUh7DT74s9cv5Rg67hJNOhUs.png",
    "display_name_prefixed": "r\/linux",
    "accounts_active": 904,
    "public_traffic": false,
    "subscribers": 441911,
    "user_flair_richtext": [
      
    ],
    "videostream_links_count": 0,
    "name": "t5_2qh1a",
    "quarantine": false,
    "hide_ads": false,
    "emojis_enabled": true,
    "advertiser_category": "Technology",
    "public_description": "All things Linux and GNU\/Linux -- this is neither a community exclusively about the kernel Linux, nor is exclusively about the GNU operating system.",
    "comment_score_hide_mins": 60,
    "user_has_favorited": false,
    "user_flair_template_id": null,
    "community_icon": "https:\/\/styles.redditmedia.com\/t5_2qh1a\/styles\/communityIcon_m00t4v99ksj11.png",
    "banner_background_image": "",
    "original_content_tag_enabled": false,
    "submit_text": "###Rules\n\n- Make sure your post is relevant to r\/linux!\n- This is **not** a support forum! Head to \/r\/linuxquestions, \/r\/linux4noobs, or other applicable subreddits for help. Looking for a distro? Try r\/findmeadistro.\n- Please submit the original article. Spamblog submissions are subject to removal and readers are encouraged to report them. See list of banned domains here: r\/linux\/wiki\/settings\/rules\/banneddomains\n- No memes, image macros or rage comics\n- No trolling, harassing, or otherwise creating a poor discussion\n- No links to sites that require a login (i.e. Facebook), URL shorteners, or otherwise misdirecting users\n- No NSFW posts.\n- Urgent spam filter requests: Message the mods and include link",
    "description_html": "&lt;!-- SC_OFF --&gt;&lt;div class=\"md\"&gt;&lt;p&gt;&lt;strong&gt;&lt;em&gt;&lt;a href=\"\/u\/curioussavage01\"&gt;u\/curioussavage01&lt;\/a&gt;&amp;#39;s &lt;a href=\"https:\/\/www.reddit.com\/r\/AskReddit\/comments\/afzfa7\/admins_of_reddit_whats_your_favorite_subreddit\/ee2yp1c\/\"&gt;favorite subreddit&lt;\/a&gt;&lt;\/em&gt;&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;h3&gt;&lt;a href=\"https:\/\/www.reddit.com\/r\/linux\/about\/rules\/\"&gt;Rules&lt;\/a&gt;&lt;\/h3&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;p&gt;&lt;strong&gt;No support requests&lt;\/strong&gt; - This is not a support forum! Head to &lt;a href=\"\/r\/linuxquestions\"&gt;\/r\/linuxquestions&lt;\/a&gt; or &lt;a href=\"\/r\/linux4noobs\"&gt;\/r\/linux4noobs&lt;\/a&gt; for support or help. Looking for a distro? Try &lt;a href=\"\/r\/findmeadistro\"&gt;r\/findmeadistro&lt;\/a&gt;.&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;strong&gt;No spamblog submissions&lt;\/strong&gt; - Posts that are identified as either blog-spam, a link aggregator, or an otherwise low-effort website are to be removed.&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;strong&gt;No memes, image macros or rage comics&lt;\/strong&gt; - Meme posts are not allowed in &lt;a href=\"\/r\/linux\"&gt;r\/linux&lt;\/a&gt;. Feel free to post over at &lt;a href=\"\/r\/linuxmemes\"&gt;\/r\/linuxmemes&lt;\/a&gt; instead.&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;strong&gt;Reddiquette, trolling, or poor discussion&lt;\/strong&gt; - &lt;a href=\"\/r\/linux\"&gt;r\/linux&lt;\/a&gt; asks all users follow &lt;a href=\"https:\/\/www.reddit.com\/wiki\/reddiquette\"&gt;Reddiquette.&lt;\/a&gt; Top violations of this rule are trolling, starting a flamewar, or not &amp;quot;remembering the human&amp;quot; aka being hostile or incredibly impolite.&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;strong&gt;Relevance to &lt;a href=\"\/r\/linux\"&gt;r\/linux&lt;\/a&gt; community&lt;\/strong&gt; - Posts should follow what the community likes: GNU\/Linux, Linux kernel itself, the developers of the kernel or open source applications, any application on Linux, and more. Take some time to get the feel of the subreddit if you&amp;#39;re not sure!&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;strong&gt;Spamming self-promotion and surveys&lt;\/strong&gt; - Submitting your own original content is welcome on &lt;a href=\"\/r\/linux\"&gt;r\/linux&lt;\/a&gt;, but we do ask that you contribute more than just your own content to the subreddit as well as require you to interact with the comments of your submission. Additionally, surveys are not allowed.&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;strong&gt;No misdirecting links, sites that require a login, or URL shorteners&lt;\/strong&gt; - In short: if your link doesn&amp;#39;t go right to the content it will be removed.&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;strong&gt;No NSFW&lt;\/strong&gt;&lt;\/p&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;p&gt;&lt;a href=\"https:\/\/www.reddit.com\/r\/linux\/about\/rules\/\"&gt;Please review full details on rules here.&lt;\/a&gt;&lt;\/p&gt;\n\n&lt;p&gt;&lt;strong&gt;GNU\/Linux is a free and open source software operating system for computers.&lt;\/strong&gt; The operating system is a collection of the basic instructions that tell the electronic parts of the computer what to do and how to work. Free, Libre and open source software (FLOSS) means that everyone has the freedom to use it, see how it works, and change it.&lt;\/p&gt;\n\n&lt;p&gt;GNU\/Linux is a collaborative effort between the GNU project, formed in 1983 to develop the GNU operating system and the development team of Linux, a kernel. Initially Linux was intended to develop into an operating system of its own, but these plans were shelved somewhere along the way.&lt;\/p&gt;\n\n&lt;p&gt;Linux is also used without GNU in embedded systems, mobile phones and appliances, often with BusyBox or other such embedded tools.&lt;\/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"http:\/\/www.gnu.org\/philosophy\/free-sw.html\"&gt;What is free software?&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"http:\/\/www.gnu.org\/gnu\/gnu.html\"&gt;Basic history of GNU\/Linux&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"http:\/\/www.nytimes.com\/1989\/01\/11\/business\/business-technology-one-man-s-fight-for-free-software.html\"&gt;One man&amp;#39;s fight for free software&lt;\/a&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;h1&gt;Join us on IRC at #&lt;a href=\"\/r\/linux\"&gt;r\/linux&lt;\/a&gt; on freenode.net!&lt;\/h1&gt;\n\n&lt;p&gt;&lt;strong&gt;Don&amp;#39;t post image-macros, rage comics, or other drivel here.&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;p&gt;&lt;strong&gt;&lt;a href=\"\/r\/linux\/wiki\/faq\/\"&gt;Frequently Asked Questions&lt;\/a&gt;&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;h1&gt;AMA&lt;\/h1&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;p&gt;&lt;a href=\"https:\/\/www.reddit.com\/r\/linux\/comments\/c1pigw\/ama_i_spent_3_years_creating_a_new_bashcompatible\/\"&gt;OilShell&lt;\/a&gt;&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;a href=\"https:\/\/www.reddit.com\/r\/linux\/comments\/brp7q7\/im_matthew_miller_fedora_project_leader_ask_me\/\"&gt;Matthew Miller (Fedora Project Lead)&lt;\/a&gt;&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;a href=\"https:\/\/reddit.com\/r\/linux\/comments\/9emwtu\/arch_linux_ama\/\"&gt;Arch Linux&lt;\/a&gt;&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;a href=\"https:\/\/reddit.com\/r\/linux\/comments\/9h5h1w\/we_are_elementary_ama\/\"&gt;elementaryOS&lt;\/a&gt;&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;a href=\"https:\/\/reddit.com\/r\/linux\/comments\/a724qe\/im_the_founder_and_lead_developer_of_bedrock\/\"&gt;Bedrock Linux&lt;\/a&gt;&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;a href=\"https:\/\/reddit.com\/r\/linux\/comments\/anspo5\/we_are_plasma_mobile_developers_ama\/\"&gt;Plasma Mobile (KDE)&lt;\/a&gt;&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;a href=\"https:\/\/www.reddit.com\/r\/linux\/comments\/as1dd0\/we_are_the_sway_wlroots_developers_ask_us_anything\/\"&gt;sway wlroots&lt;\/a&gt;&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;a href=\"https:\/\/www.reddit.com\/r\/linux\/comments\/2ny1lz\/im_greg_kroahhartman_linux_kernel_developer_ama\/\"&gt;Greg Kroah-Hartman&lt;\/a&gt;&lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;&lt;a href=\"https:\/\/www.reddit.com\/r\/linux\/wiki\/calendar\"&gt;For more AMAs, check out our wiki link by clicking here.&lt;\/a&gt;&lt;\/p&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;h1&gt;GNU\/Linux resources:&lt;\/h1&gt;\n\n&lt;p&gt;&lt;strong&gt;GNU\/Linux Related:&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"\/r\/kernel\"&gt;Kernel&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/gnu\"&gt;GNU&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/linuxadmin\"&gt;LinuxAdmin&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/linuxdev\"&gt;LinuxDev&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/linux_devices\"&gt;LinuxDevices&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/freegaming\"&gt;FreeGaming&lt;\/a&gt; \/ &lt;a href=\"\/r\/opensourcegames\"&gt;OpenSourceGames&lt;\/a&gt; \/ &lt;a href=\"\/r\/linux_gaming\"&gt;LinuxGaming&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/linuxmemes\"&gt;LinuxMemes&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/linuxquestions\"&gt;LinuxQuestions&lt;\/a&gt; \/ &lt;a href=\"\/r\/linux4noobs\"&gt;Linux4noobs&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/DistroHopping\"&gt;DistroHopping&lt;\/a&gt; \/ &lt;a href=\"\/r\/distroreviews\"&gt;DistroReviews&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"http:\/\/lwn.net\"&gt;Linux Weekly News&lt;sup&gt;[\u2197]&lt;\/sup&gt;&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/LinuxHardware\"&gt;Linux Hardware&lt;\/a&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;p&gt;&lt;strong&gt;Distributions:&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"\/r\/archlinux\"&gt;Arch&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/bedrocklinux\"&gt;Bedrock&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/BunsenLabs\"&gt;Bunsen Labs&lt;\/a&gt; \/ &lt;a href=\"\/r\/crunchbang\"&gt;CrunchBang&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/centos\"&gt;CentOS&lt;\/a&gt; \/ &lt;a href=\"\/r\/redhat\"&gt;Red Hat&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/chromeos\"&gt;Chrome OS&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/debian\"&gt;Debian&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/elementaryos\"&gt;elementary OS&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/fedora\"&gt;Fedora&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/gentoo\"&gt;Gentoo&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/linuxmint\"&gt;LinuxMint&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/lubuntu\"&gt;Lubuntu&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/ManjaroLinux\"&gt;Manjaro&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/mxlinux\"&gt;MX Linux&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/NixOS\"&gt;NixOS&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/opensuse\"&gt;openSUSE&lt;\/a&gt; \/ &lt;a href=\"\/r\/suse\"&gt;SUSE&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/openwrt\"&gt;OpenWRT&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/slackware\"&gt;Slackware&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/solusproject\"&gt;Solus&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/ubuntu\"&gt;Ubuntu&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/xubuntu\"&gt;Xubuntu&lt;\/a&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;p&gt;&lt;strong&gt;Linux on Mobile:&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"\/r\/android\"&gt;Android&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/firefoxos\"&gt;FirefoxOS&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/jolla\"&gt;Jolla (SailfishOS)&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/meego\"&gt;MeeGo&lt;\/a&gt; \/ &lt;a href=\"\/r\/maemo\"&gt;Maemo&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/postmarketOS\"&gt;PostmarketOS&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"http:\/\/replicant.us\/\"&gt;Replicant&lt;sup&gt;[\u2197]&lt;\/sup&gt;&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/ubuntuphone\"&gt;UbuntuPhone&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/ZeroPhone\"&gt;ZeroPhone&lt;\/a&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;p&gt;&lt;strong&gt;Movements:&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"\/r\/freeculture\"&gt;Free Culture&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/freesoftware\"&gt;Free Software&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/HackBloc\"&gt;Hack Bloc&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/opensource\"&gt;Open Source&lt;\/a&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;p&gt;&lt;strong&gt;Desktop Environments:&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"\/r\/CinnamonDE\"&gt;Cinnamon&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/enlightenmentde\"&gt;Enlightenment&lt;\/a&gt; \/ &lt;a href=\"\/r\/e17\"&gt;e17&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/gnome\"&gt;GNOME&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/kde\"&gt;KDE&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/lxde\"&gt;LXDE&lt;\/a&gt; \/ &lt;a href=\"\/r\/LXQt\"&gt;LXQt&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/xfce\"&gt;XFCE&lt;\/a&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;p&gt;&lt;strong&gt;Window Managers:&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"\/r\/awesomewm\"&gt;awesomewm&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/openbox\"&gt;Openbox&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/xmonad\"&gt;xmonad&lt;\/a&gt;&lt;br\/&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/i3wm\/\"&gt;i3&lt;\/a&gt;&lt;br\/&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/unixporn\"&gt;UnixPorn&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/UsabilityPorn\"&gt;UsabilityPorn&lt;\/a&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;p&gt;&lt;strong&gt;Learning:&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"\/r\/AskLinuxUsers\"&gt;Ask Linux Users&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/commandline\"&gt;CommandLine&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/linuxfromscratch\"&gt;Linux From Scratch&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/linuxprojects\"&gt;Linux Projects&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"http:\/\/www.softwarefreedom.org\/resources\/\"&gt;Software Freedom Law Center&lt;sup&gt;[\u2197]&lt;\/sup&gt;&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/raspberry_pi\"&gt;Raspberry Pi&lt;\/a&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;p&gt;&lt;strong&gt;Webcasts:&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"https:\/\/www.youtube.com\/user\/teksyndicate\"&gt;Level 1 Techs&lt;sup&gt;[\u2197]&lt;\/sup&gt;&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"https:\/\/www.youtube.com\/user\/BryanLunduke\"&gt;The Lunduke Hour&lt;sup&gt;[\u2197]&lt;\/sup&gt;&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/linuxunplugged\"&gt;Linux Unplugged (formerly Linux Action Show)&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"http:\/\/www.badvoltage.org\/\"&gt;Bad Voltage&lt;sup&gt;[\u2197]&lt;\/sup&gt;&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"http:\/\/hackerpublicradio.org\/\"&gt;Hacker Public Radio&lt;sup&gt;[\u2197]&lt;\/sup&gt;&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"http:\/\/faif.us\/\"&gt;FaiFCast&lt;sup&gt;[\u2197]&lt;\/sup&gt;&lt;\/a&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;p&gt;&lt;strong&gt;Creativity:&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"\/r\/blender\"&gt;Blender&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/fossphotography\"&gt;FOSSPhotography&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/gimp\"&gt;GIMP&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/krita\"&gt;Krita&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/libredesign\"&gt;LibreDesign&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/libreoffice\/\"&gt;LibreOffice&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/librestudio\"&gt;LibreStudio&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/linuxaudio\"&gt;LinuxAudio&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/Linux_Filmmaking\"&gt;LinuxFilmMaking&lt;\/a&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;p&gt;&lt;strong&gt;Other operating systems:&lt;\/strong&gt;&lt;\/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;a href=\"http:\/\/aros.sourceforge.net\/\"&gt;AROS&lt;sup&gt;[\u2197]&lt;\/sup&gt;&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/bsd\"&gt;BSD&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/freebsd\"&gt;FreeBSD&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"https:\/\/genode.org\"&gt;Genode&lt;sup&gt;[\u2197]&lt;\/sup&gt;&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/haikuos\"&gt;Haiku&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"https:\/\/helenos.org\"&gt;HelenOS&lt;sup&gt;[\u2197]&lt;\/sup&gt;&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/hurd\"&gt;HURD&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/minix\"&gt;Minix&lt;\/a&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/plan9\"&gt;Plan 9&lt;\/a&gt;&lt;br\/&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;a href=\"\/r\/osdev\"&gt;OSdev&lt;\/a&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;p&gt;&lt;strong&gt;Don&amp;#39;t see your community listed?&lt;\/strong&gt; &lt;a href=\"http:\/\/reddit.com\/search\"&gt;Find&lt;\/a&gt; or &lt;a href=\"http:\/\/www.reddit.com\/reddits\/create\"&gt;create&lt;\/a&gt; a subreddit for it, and &lt;a href=\"http:\/\/www.reddit.com\/r\/linux\/wiki\/edit\/index\"&gt;add it to the wiki&lt;\/a&gt; when it has over 350 subscribers, and we&amp;#39;ll add it to the sidebar.&lt;\/p&gt;\n&lt;\/div&gt;&lt;!-- SC_ON --&gt;",
    "spoilers_enabled": true,
    "header_title": "Hello, this is Linus Torvalds and I pronounce Linux as 'linucks'.",
    "header_size": [
      120,
      40
    ],
    "user_flair_position": "right",
    "all_original_content": false,
    "has_menu_widget": true,
    "is_enrolled_in_new_modmail": null,
    "key_color": "#222222",
    "can_assign_user_flair": true,
    "created": 1201260666,
    "wls": 6,
    "show_media_preview": true,
    "submission_type": "any",
    "user_is_subscriber": true,
    "disable_contributor_requests": false,
    "allow_videogifs": true,
    "user_flair_type": "text",
    "collapse_deleted_comments": true,
    "emojis_custom_size": null,
    "public_description_html": "&lt;!-- SC_OFF --&gt;&lt;div class=\"md\"&gt;&lt;p&gt;All things Linux and GNU\/Linux -- this is neither a community exclusively about the kernel Linux, nor is exclusively about the GNU operating system.&lt;\/p&gt;\n&lt;\/div&gt;&lt;!-- SC_ON --&gt;",
    "allow_videos": true,
    "is_crosspostable_subreddit": true,
    "notification_level": "low",
    "can_assign_link_flair": true,
    "accounts_active_is_fuzzed": false,
    "submit_text_label": null,
    "link_flair_position": "left",
    "user_sr_flair_enabled": true,
    "user_flair_enabled_in_sr": true,
    "allow_discovery": true,
    "user_sr_theme_enabled": true,
    "link_flair_enabled": true,
    "subreddit_type": "public",
    "suggested_comment_sort": null,
    "banner_img": "",
    "user_flair_text": null,
    "banner_background_color": "#282a36",
    "show_media": false,
    "id": "2qh1a",
    "user_is_moderator": false,
    "over18": true,
    "description": "***u\/curioussavage01's [favorite subreddit](https:\/\/www.reddit.com\/r\/AskReddit\/comments\/afzfa7\/admins_of_reddit_whats_your_favorite_subreddit\/ee2yp1c\/)***\n\n###[Rules](https:\/\/www.reddit.com\/r\/linux\/about\/rules\/)\n\n*   **No support requests** - This is not a support forum! Head to \/r\/linuxquestions or \/r\/linux4noobs for support or help. Looking for a distro? Try r\/findmeadistro.\n\n*    **No spamblog submissions** - Posts that are identified as either blog-spam, a link aggregator, or an otherwise low-effort website are to be removed.\n\n*   **No memes, image macros or rage comics** - Meme posts are not allowed in r\/linux. Feel free to post over at \/r\/linuxmemes instead.\n\n*    **Reddiquette, trolling, or poor discussion** - r\/linux asks all users follow [Reddiquette.](https:\/\/www.reddit.com\/wiki\/reddiquette) Top violations of this rule are trolling, starting a flamewar, or not \"remembering the human\" aka being hostile or incredibly impolite.\n\n* **Relevance to r\/linux community** - Posts should follow what the community likes: GNU\/Linux, Linux kernel itself, the developers of the kernel or open source applications, any application on Linux, and more. Take some time to get the feel of the subreddit if you're not sure!\n\n*    **Spamming self-promotion and surveys** - Submitting your own original content is welcome on r\/linux, but we do ask that you contribute more than just your own content to the subreddit as well as require you to interact with the comments of your submission. Additionally, surveys are not allowed.\n\n*    **No misdirecting links, sites that require a login, or URL shorteners** - In short: if your link doesn't go right to the content it will be removed.\n\n*    **No NSFW**\n\n[Please review full details on rules here.](https:\/\/www.reddit.com\/r\/linux\/about\/rules\/)\n\n**GNU\/Linux is a free and open source software operating system for computers.** The operating system is a collection of the basic instructions that tell the electronic parts of the computer what to do and how to work. Free, Libre and open source software (FLOSS) means that everyone has the freedom to use it, see how it works, and change it.\n\nGNU\/Linux is a collaborative effort between the GNU project, formed in 1983 to develop the GNU operating system and the development team of Linux, a kernel. Initially Linux was intended to develop into an operating system of its own, but these plans were shelved somewhere along the way.\n\nLinux is also used without GNU in embedded systems, mobile phones and appliances, often with BusyBox or other such embedded tools.\n\n* [What is free software?](http:\/\/www.gnu.org\/philosophy\/free-sw.html)\n* [Basic history of GNU\/Linux](http:\/\/www.gnu.org\/gnu\/gnu.html)\n* [One man's fight for free software](http:\/\/www.nytimes.com\/1989\/01\/11\/business\/business-technology-one-man-s-fight-for-free-software.html)\n\n# Join us on IRC at #r\/linux on freenode.net! \n\n**Don't post image-macros, rage comics, or other drivel here.**\n\n**[Frequently Asked Questions](\/r\/linux\/wiki\/faq\/)**\n\n# AMA\n\n* [OilShell](https:\/\/www.reddit.com\/r\/linux\/comments\/c1pigw\/ama_i_spent_3_years_creating_a_new_bashcompatible\/)\n\n* [Matthew Miller (Fedora Project Lead)](https:\/\/www.reddit.com\/r\/linux\/comments\/brp7q7\/im_matthew_miller_fedora_project_leader_ask_me\/)\n\n* [Arch Linux](https:\/\/reddit.com\/r\/linux\/comments\/9emwtu\/arch_linux_ama\/)\n\n* [elementaryOS](https:\/\/reddit.com\/r\/linux\/comments\/9h5h1w\/we_are_elementary_ama\/)\n\n* [Bedrock Linux](https:\/\/reddit.com\/r\/linux\/comments\/a724qe\/im_the_founder_and_lead_developer_of_bedrock\/)\n\n* [Plasma Mobile (KDE)](https:\/\/reddit.com\/r\/linux\/comments\/anspo5\/we_are_plasma_mobile_developers_ama\/)\n\n* [sway wlroots](https:\/\/www.reddit.com\/r\/linux\/comments\/as1dd0\/we_are_the_sway_wlroots_developers_ask_us_anything\/)\n\n* [Greg Kroah-Hartman](https:\/\/www.reddit.com\/r\/linux\/comments\/2ny1lz\/im_greg_kroahhartman_linux_kernel_developer_ama\/)\n\n* [For more AMAs, check out our wiki link by clicking here.](https:\/\/www.reddit.com\/r\/linux\/wiki\/calendar)\n\n# GNU\/Linux resources:\n\n**GNU\/Linux Related:**\n\n* [Kernel](\/r\/kernel)\n* [GNU](\/r\/gnu)\n* [LinuxAdmin](\/r\/linuxadmin)\n* [LinuxDev](\/r\/linuxdev)\n* [LinuxDevices](\/r\/linux_devices)\n* [FreeGaming](\/r\/freegaming) \/ [OpenSourceGames](\/r\/opensourcegames) \/ [LinuxGaming](\/r\/linux_gaming)\n* [LinuxMemes](\/r\/linuxmemes)\n* [LinuxQuestions](\/r\/linuxquestions) \/ [Linux4noobs](\/r\/linux4noobs)\n* [DistroHopping](\/r\/DistroHopping) \/ [DistroReviews](\/r\/distroreviews)\n* [Linux Weekly News^\\[\u2197\\]](http:\/\/lwn.net)\n* [Linux Hardware](\/r\/LinuxHardware)\n\n**Distributions:**\n\n* [Arch](\/r\/archlinux)\n* [Bedrock](\/r\/bedrocklinux)\n* [Bunsen Labs](\/r\/BunsenLabs) \/ [CrunchBang](\/r\/crunchbang)\n* [CentOS](\/r\/centos) \/ [Red Hat](\/r\/redhat)\n* [Chrome OS](\/r\/chromeos)\n* [Debian](\/r\/debian)\n* [elementary OS](\/r\/elementaryos)\n* [Fedora](\/r\/fedora)\n* [Gentoo](\/r\/gentoo)\n* [LinuxMint](\/r\/linuxmint)\n* [Lubuntu](\/r\/lubuntu)\n* [Manjaro](\/r\/ManjaroLinux)\n* [MX Linux](\/r\/mxlinux)\n* [NixOS](\/r\/NixOS)\n* [openSUSE](\/r\/opensuse) \/ [SUSE](\/r\/suse)\n* [OpenWRT](\/r\/openwrt)\n* [Slackware](\/r\/slackware)\n* [Solus](\/r\/solusproject)\n* [Ubuntu](\/r\/ubuntu)\n* [Xubuntu](\/r\/xubuntu)\n\n\n**Linux on Mobile:**\n\n* [Android](\/r\/android)\n* [FirefoxOS](\/r\/firefoxos)\n* [Jolla (SailfishOS)](\/r\/jolla)\n* [MeeGo](\/r\/meego) \/ [Maemo](\/r\/maemo)\n* [PostmarketOS](\/r\/postmarketOS)\n* [Replicant^\\[\u2197\\]](http:\/\/replicant.us\/)\n* [UbuntuPhone](\/r\/ubuntuphone)\n* [ZeroPhone](\/r\/ZeroPhone)\n\n**Movements:**\n\n* [Free Culture](\/r\/freeculture)\n* [Free Software](\/r\/freesoftware)\n* [Hack Bloc](\/r\/HackBloc)\n* [Open Source](\/r\/opensource)\n\n**Desktop Environments:**\n\n* [Cinnamon](\/r\/CinnamonDE)\n* [Enlightenment](\/r\/enlightenmentde) \/ [e17](\/r\/e17)\n* [GNOME](\/r\/gnome)\n* [KDE](\/r\/kde)\n* [LXDE](\/r\/lxde) \/ [LXQt](\/r\/LXQt)\n* [XFCE](\/r\/xfce)\n\n**Window Managers:**\n\n* [awesomewm](\/r\/awesomewm)\n* [Openbox](\/r\/openbox)\n* [xmonad](\/r\/xmonad)  \n* [i3](\/r\/i3wm\/)  \n* [UnixPorn](\/r\/unixporn)\n* [UsabilityPorn](\/r\/UsabilityPorn)\n\n**Learning:**\n\n* [Ask Linux Users](\/r\/AskLinuxUsers)\n* [CommandLine](\/r\/commandline)\n* [Linux From Scratch](\/r\/linuxfromscratch)\n* [Linux Projects](\/r\/linuxprojects)\n* [Software Freedom Law Center^\\[\u2197\\]](http:\/\/www.softwarefreedom.org\/resources\/)\n* [Raspberry Pi](\/r\/raspberry_pi)\n\n**Webcasts:**\n\n* [Level 1 Techs^\\[\u2197\\]](https:\/\/www.youtube.com\/user\/teksyndicate)\n* [The Lunduke Hour^\\[\u2197\\]](https:\/\/www.youtube.com\/user\/BryanLunduke)\n* [Linux Unplugged (formerly Linux Action Show)](\/r\/linuxunplugged)\n* [Bad Voltage^\\[\u2197\\]](http:\/\/www.badvoltage.org\/)\n* [Hacker Public Radio^\\[\u2197\\]](http:\/\/hackerpublicradio.org\/)\n* [FaiFCast^\\[\u2197\\]](http:\/\/faif.us\/)\n\n**Creativity:**\n\n* [Blender](\/r\/blender)\n* [FOSSPhotography](\/r\/fossphotography)\n* [GIMP](\/r\/gimp)\n* [Krita](\/r\/krita)\n* [LibreDesign](\/r\/libredesign)\n* [LibreOffice](\/r\/libreoffice\/)\n* [LibreStudio](\/r\/librestudio)\n* [LinuxAudio](\/r\/linuxaudio)\n* [LinuxFilmMaking](\/r\/Linux_Filmmaking)\n\n**Other operating systems:**\n\n* [AROS^\\[\u2197\\]](http:\/\/aros.sourceforge.net\/)\n* [BSD](\/r\/bsd)\n* [FreeBSD](\/r\/freebsd)\n* [Genode^\\[\u2197\\]](https:\/\/genode.org)\n* [Haiku](\/r\/haikuos)\n* [HelenOS^\\[\u2197\\]](https:\/\/helenos.org)\n* [HURD](\/r\/hurd)\n* [Minix](\/r\/minix)\n* [Plan 9](\/r\/plan9)  \n* [OSdev](\/r\/osdev)\n\n**Don't see your community listed?** [Find](http:\/\/reddit.com\/search) or [create](http:\/\/www.reddit.com\/reddits\/create) a subreddit for it, and [add it to the wiki](http:\/\/www.reddit.com\/r\/linux\/wiki\/edit\/index) when it has over 350 subscribers, and we'll add it to the sidebar.",
    "submit_link_label": null,
    "user_flair_text_color": null,
    "restrict_commenting": false,
    "user_flair_css_class": null,
    "allow_images": true,
    "lang": "en",
    "whitelist_status": "all_ads",
    "url": "\/r\/linux\/",
    "created_utc": 1201231866,
    "banner_size": null,
    "mobile_banner_image": "",
    "user_is_contributor": false
  }
}"""
    }
}
