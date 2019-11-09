package com.example.redfruit.adapter

import com.example.redfruit.data.adapter.PostAdapter
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.images.ImageSource
import com.squareup.moshi.Moshi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PostAdapterTest {

    val moshi = Moshi.Builder().add(PostAdapter()).build()
    val adapter = moshi.adapter(Post::class.java)

    @Test
    fun testDeserialization() {

        val post = adapter.fromJson(PostString)!!

        assertEquals("MsElia", post.author)

        assertEquals(
            "Finally got all my dream Lancer, mine wish has become true, wish you guys got your dream Servant too.",
            post.title
        )

        assertFalse(post.stickied)
        assertTrue(post.over_18)

        val imageSource = ImageSource(
            "https://preview.redd.it/efxkoi0c15m31.jpg?auto=webp&amp;s=92ce8de5d3d2e35f7eedd8be01c79e063a770666",
            500,
            534
        )
        assertEquals(imageSource, post.preview.firstImage?.source)

        assertEquals("1", post.score)

        assertEquals("d35r85", post.id)

        assertEquals("https://i.redd.it/efxkoi0c15m31.jpg", post.url)

        assertNull(post.secureMedia, "Media must be null")

        assertEquals("2", post.num_comments)
    }


    companion object {
        private const val PostString = """{"kind": "t3", "data": {"approved_at_utc": null, "subreddit": "grandorder", "selftext": "", "author_fullname": "t2_26jrprg8", "saved": false, "mod_reason_title": null, "gilded": 0, "clicked": false, "title": "Finally got all my dream Lancer, mine wish has become true, wish you guys got your dream Servant too.", "link_flair_richtext": [{"e": "text", "t": "OC"}], "subreddit_name_prefixed": "r/grandorder", "hidden": false, "pwls": 6, "link_flair_css_class": "b", "downs": 0, "thumbnail_height": 140, "hide_score": true, "name": "t3_d35r85", "quarantine": false, "link_flair_text_color": "light", "author_flair_background_color": null, "subreddit_type": "public", "ups": 1, "total_awards_received": 0, "media_embed": {}, "thumbnail_width": 140, "author_flair_template_id": null, "is_original_content": false, "user_reports": [], "secure_media": null, "is_reddit_media_domain": true, "is_meta": false, "category": null, "secure_media_embed": {}, "link_flair_text": "OC", "can_mod_post": false, "score": 1, "approved_by": null, "thumbnail": "https://b.thumbs.redditmedia.com/pLTpjZE4_AwLEfcfWVaRcOIodxRFRrfxR-L9Cfwli5Y.jpg", "edited": false, "author_flair_css_class": null, "steward_reports": [], "author_flair_richtext": [], "gildings": {}, "post_hint": "image", "content_categories": null, "is_self": false, "mod_note": null, "created": 1568311904.0, "link_flair_type": "richtext", "wls": 6, "banned_by": null, "author_flair_type": "text", "domain": "i.redd.it", "allow_live_comments": false, "selftext_html": null, "likes": null, "suggested_sort": null, "banned_at_utc": null, "view_count": null, "archived": false, "no_follow": true, "is_crosspostable": true, "pinned": false, "over_18": true, "preview": {"images": [{"source": {"url": "https://preview.redd.it/efxkoi0c15m31.jpg?auto=webp&amp;s=92ce8de5d3d2e35f7eedd8be01c79e063a770666", "width": 500, "height": 534}, "resolutions": [{"url": "https://preview.redd.it/efxkoi0c15m31.jpg?width=108&amp;crop=smart&amp;auto=webp&amp;s=929c62b92e95b3817b6a1c21a5f18f3d04140332", "width": 108, "height": 115}, {"url": "https://preview.redd.it/efxkoi0c15m31.jpg?width=216&amp;crop=smart&amp;auto=webp&amp;s=9bcee0ec78be4c08d3bf5c8460e72cfe85bb3ee2", "width": 216, "height": 230}, {"url": "https://preview.redd.it/efxkoi0c15m31.jpg?width=320&amp;crop=smart&amp;auto=webp&amp;s=0b70186143756d2a432cdf950fc3e0096335e707", "width": 320, "height": 341}], "variants": {}, "id": "OiP2yAP7v1_n62sArVvkREceQrwQpP5McqBofZ79asE"}], "enabled": true}, "all_awardings": [], "media_only": false, "link_flair_template_id": "ceb73052-5a6d-11e7-a13f-0ef01c75cf02", "can_gild": true, "spoiler": false, "locked": false, "author_flair_text": null, "visited": false, "num_reports": null, "distinguished": null, "subreddit_id": "t5_39d7x", "mod_reason_by": null, "removal_reason": null, "link_flair_background_color": "#ff66ac", "id": "d35r85", "is_robot_indexable": true, "report_reasons": null, "author": "MsElia", "num_crossposts": 0, "num_comments": 2, "send_replies": true, "whitelist_status": "all_ads", "contest_mode": false, "mod_reports": [], "author_patreon_flair": false, "author_flair_text_color": null, "permalink": "/r/grandorder/comments/d35r85/finally_got_all_my_dream_lancer_mine_wish_has/", "parent_whitelist_status": "all_ads", "stickied": false, "url": "https://i.redd.it/efxkoi0c15m31.jpg", "subreddit_subscribers": 112171, "created_utc": 1568283104.0, "discussion_type": null, "media": null, "is_video": false}}"""
    }
}