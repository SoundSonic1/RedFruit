package com.example.redfruit.adapter

import com.example.redfruit.data.adapter.SubredditListingAdapter
import com.example.redfruit.data.model.SubredditListing
import com.squareup.moshi.Moshi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class SubredditListingAdapterTest {

    private val moshi = Moshi.Builder().add(SubredditListingAdapter()).build()
    private val adapter = moshi.adapter(SubredditListing::class.java)

    @Test
    fun `deserialize valid data`() {
        val subredditListing =
            adapter.fromJson(validString) ?: fail("SubredditListing must not be null")

        assertEquals("Android", subredditListing.name)

        assertEquals("", subredditListing.before)

        assertEquals("t3_dtnnb3", subredditListing.after)

        assertEquals(1, subredditListing.children.size)
    }

    @Test
    fun `invalid kind`() {
        assertNull(adapter.fromJson("""{"kind": "t3"}"""))
        assertNull(adapter.fromJson("""{"kind": null}"""))
    }

    @Test
    fun `empty data`() {
        assertNull(adapter.fromJson("""{"kind": "Listing", "data": {}}"""))
        assertNull(adapter.fromJson("""{"kind": "Listing", "data": []}"""))
    }

    companion object {

       private const val validString = """{
  "kind": "Listing",
  "data": {
    "modhash": "crrpz9bgqb837a3badb94e44809fd36ec78aa8113bac851b58",
    "dist": 1,
    "children": [
      {
        "kind": "t3",
        "data": {
          "approved_at_utc": null,
          "subreddit": "Android",
          "selftext": "Note 1. Join our Discord, IRC, and Telegram chat-rooms! [Please see our wiki for instructions.](https:\/\/www.reddit.com\/r\/Android\/wiki\/index#wiki_.2Fr.2Fandroid_chat_rooms)\n\nThis weekly Sunday thread is for you to let off some steam and speak out about whatever complaint you might have about:  \n\n* Your device.  \n\n* Your carrier.  \n\n* Your device's manufacturer.  \n\n* An app  \n\n* Any other company\n\n***  \n\n**Rules**  \n\n1) Please do not target any individuals or try to name\/shame any individual. If you hate Google\/Samsung\/HTC etc. for one thing that is fine, but do not be rude to an individual app developer.\n\n2) If you have a suggestion to solve another user's issue, please leave a comment but be sure it's constructive! We do not want any flame-wars.  \n\n3) Be respectful of other's opinions. Even if you feel that somebody is \"wrong\" you don't have to go out of your way to prove them wrong. Disagree politely, and move on.",
          "author_fullname": "t2_6l4z3",
          "saved": false,
          "mod_reason_title": null,
          "gilded": 0,
          "clicked": false,
          "title": "Sunday Rant\/Rage (Nov 10 2019) - Your weekly complaint thread!",
          "link_flair_richtext": [
            
          ],
          "subreddit_name_prefixed": "r\/Android",
          "hidden": false,
          "pwls": 6,
          "link_flair_css_class": null,
          "downs": 0,
          "thumbnail_height": null,
          "hide_score": false,
          "name": "t3_dua7gl",
          "quarantine": false,
          "link_flair_text_color": "dark",
          "author_flair_background_color": "",
          "subreddit_type": "public",
          "ups": 13,
          "total_awards_received": 0,
          "media_embed": {
            
          },
          "thumbnail_width": null,
          "author_flair_template_id": null,
          "is_original_content": false,
          "user_reports": [
            
          ],
          "secure_media": null,
          "is_reddit_media_domain": false,
          "is_meta": false,
          "category": null,
          "secure_media_embed": {
            
          },
          "link_flair_text": null,
          "can_mod_post": false,
          "score": 13,
          "approved_by": null,
          "thumbnail": "self",
          "edited": false,
          "author_flair_css_class": "robot",
          "steward_reports": [
            
          ],
          "author_flair_richtext": [
            
          ],
          "gildings": {
            
          },
          "content_categories": null,
          "is_self": true,
          "mod_note": null,
          "created": 1573413070,
          "link_flair_type": "text",
          "wls": 6,
          "banned_by": null,
          "author_flair_type": "text",
          "domain": "self.Android",
          "allow_live_comments": false,
          "selftext_html": "&lt;!-- SC_OFF --&gt;&lt;div class=\"md\"&gt;&lt;p&gt;Note 1. Join our Discord, IRC, and Telegram chat-rooms! &lt;a href=\"https:\/\/www.reddit.com\/r\/Android\/wiki\/index#wiki_.2Fr.2Fandroid_chat_rooms\"&gt;Please see our wiki for instructions.&lt;\/a&gt;&lt;\/p&gt;\n\n&lt;p&gt;This weekly Sunday thread is for you to let off some steam and speak out about whatever complaint you might have about:  &lt;\/p&gt;\n\n&lt;ul&gt;\n&lt;li&gt;&lt;p&gt;Your device.  &lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;Your carrier.  &lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;Your device&amp;#39;s manufacturer.  &lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;An app  &lt;\/p&gt;&lt;\/li&gt;\n&lt;li&gt;&lt;p&gt;Any other company&lt;\/p&gt;&lt;\/li&gt;\n&lt;\/ul&gt;\n\n&lt;hr\/&gt;\n\n&lt;p&gt;&lt;strong&gt;Rules&lt;\/strong&gt;  &lt;\/p&gt;\n\n&lt;p&gt;1) Please do not target any individuals or try to name\/shame any individual. If you hate Google\/Samsung\/HTC etc. for one thing that is fine, but do not be rude to an individual app developer.&lt;\/p&gt;\n\n&lt;p&gt;2) If you have a suggestion to solve another user&amp;#39;s issue, please leave a comment but be sure it&amp;#39;s constructive! We do not want any flame-wars.  &lt;\/p&gt;\n\n&lt;p&gt;3) Be respectful of other&amp;#39;s opinions. Even if you feel that somebody is &amp;quot;wrong&amp;quot; you don&amp;#39;t have to go out of your way to prove them wrong. Disagree politely, and move on.&lt;\/p&gt;\n&lt;\/div&gt;&lt;!-- SC_ON --&gt;",
          "likes": null,
          "suggested_sort": null,
          "banned_at_utc": null,
          "view_count": null,
          "archived": false,
          "no_follow": false,
          "is_crosspostable": true,
          "pinned": false,
          "over_18": false,
          "all_awardings": [
            
          ],
          "awarders": [
            
          ],
          "media_only": false,
          "can_gild": true,
          "spoiler": false,
          "locked": false,
          "author_flair_text": "*beep boop*",
          "visited": false,
          "num_reports": null,
          "distinguished": "moderator",
          "subreddit_id": "t5_2qlqh",
          "mod_reason_by": null,
          "removal_reason": null,
          "link_flair_background_color": "",
          "id": "dua7gl",
          "is_robot_indexable": true,
          "report_reasons": null,
          "author": "AutoModerator",
          "discussion_type": null,
          "num_comments": 62,
          "send_replies": false,
          "whitelist_status": "all_ads",
          "contest_mode": false,
          "mod_reports": [
            
          ],
          "author_patreon_flair": false,
          "author_flair_text_color": "dark",
          "permalink": "\/r\/Android\/comments\/dua7gl\/sunday_rantrage_nov_10_2019_your_weekly_complaint\/",
          "parent_whitelist_status": "all_ads",
          "stickied": true,
          "url": "https:\/\/www.reddit.com\/r\/Android\/comments\/dua7gl\/sunday_rantrage_nov_10_2019_your_weekly_complaint\/",
          "subreddit_subscribers": 1920544,
          "created_utc": 1573384270,
          "num_crossposts": 0,
          "media": null,
          "is_video": false
        }
      }],
      "after": "t3_dtnnb3",
      "before": null
      }
}"""
    }
}
