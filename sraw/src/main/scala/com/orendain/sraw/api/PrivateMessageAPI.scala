package com.orendain.sraw.api

import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import com.orendain.sraw.model._
import com.orendain.sraw.request._

/**
 *
 * @see http://www.reddit.com/dev/api#section_messages
 */
object PrivateMessageAPI {

  // Blueprints
  val BlockBP = RequestStubBlueprint(POST, "/api/block", Scope.PrivateMessages)
  val ComposeBP = RequestStubBlueprint(POST, "/api/compose", Scope.PrivateMessages)
  val ReadAllBP = RequestStubBlueprint(POST, "/api/read_all_messages", Scope.PrivateMessages)
  val ReadMessageBP = RequestStubBlueprint(POST, "/api/read_message", Scope.PrivateMessages)
  val UnblockSubredditBP = RequestStubBlueprint(POST, "/api/unblock_subreddit", Scope.PrivateMessages)
  val UnreadMessageBP = RequestStubBlueprint(POST, "/api/unread_message", Scope.PrivateMessages)
  val InboxBP = RequestStubBlueprint(GET, "/message/inbox", Scope.PrivateMessages)
  val UnreadBP = RequestStubBlueprint(GET, "/message/unread", Scope.PrivateMessages)
  val SentBP = RequestStubBlueprint(GET, "/message/sent", Scope.PrivateMessages)

  /**
    * Accesses the endpoint: POST /api/block
    *
    * Requires [[Scope.PrivateMessages]] access.
    *
    * @param fullname the fullname of the private message whose user to block.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: For blocking via inbox.
    * @see http://www.reddit.com/dev/api#POST_api_block
    */
  def block(fullname: String) =
    BlockBP.instantiate(
        input = RequestInput("id" -> fullname),
        extractor = RedditNothing)

  // {"json": {"captcha": "Jc5l1nm0r4DbL9Rb44N1frRS2SA2JrAU", "errors": [["USER_BLOCKED", "you can't send to a user that you have blocked", "to"], ["BAD_CAPTCHA", "care to try these again?", "captcha"]]}}
  /**
    * Accesses the endpoint: POST /api/compose
    *
    * Requires [[Scope.PrivateMessages]] access.
    *
    * @param to username to message
    * @param subject the message subject
    * @param text the message content
    * @param captcha a [[FilledCaptcha]] object
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Handles message composition under /message/compose.
    * @see http://www.reddit.com/dev/api#POST_api_compose
    */
  def composeMessage(to: String, subject: String, text: String, captcha: FilledCaptcha) =
    ComposeBP.instantiate(
        input = RequestInput(
            ("api_type" -> "json"),
            ("iden" -> captcha.iden),
            ("captcha" -> captcha.response),
            ("to" -> to),
            ("subject" -> subject),
            ("text" -> text)),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/compose
    *
    * Requires [[Scope.PrivateMessages]] access.
    *
    * @param to username to message
    * @param subject the message subject
    * @param text the message content
    * @param subreddit the subreddit to message as
    * @param captcha a [[FilledCaptcha]] object
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Handles message composition under /message/compose.
    * @see http://www.reddit.com/dev/api#POST_api_compose
    */
  def composeSubredditMessage(to: String, subject: String, text: String, subreddit: String, captcha: FilledCaptcha) =
    ComposeBP.instantiate(
        input = RequestInput(
            ("api_type" -> "json"),
            ("iden" -> captcha.iden),
            ("captcha" -> captcha.response),
            ("to" -> to),
            ("subject" -> subject),
            ("text" -> text),
            ("from_sr" -> subreddit)),
        extractor = RedditNothing)

  // This means our signal was acknowledged, queued up for execution
  // MISSED = 202 Accepted
  // TODO: do
  /**
    * Accesses the endpoint: POST /api/read_all_messages
    *
    * Requires [[Scope.PrivateMessages]] access.
    *
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @note From Reddit API docs: Queue up marking all messages for a user as read.
    * @see http://www.reddit.com/dev/api#POST_api_read_all_messages
    */
  def readAll() = ReadAllBP.instantiate(extractor = RedditNothing)

  // returns a 500 error when used ... same when we try to block subreddit via blocK().
  // hmmm...
  def unblockSubreddit(fullname: String) =
    UnblockSubredditBP.instantiate(input = RequestInput("id" -> fullname))

  /**
    * Accesses the endpoint: POST /api/read_message
    *
    * Mark messages as read.
    *
    * Requires [[Scope.PrivateMessages]] access.
    *
    * @param fullnames a sequence of fullnames corresponding to messages to mark as read.
    * @return a [[RequestStub]] that, when processed, yields a [[RedditNothing]].
    * @see http://www.reddit.com/dev/api#POST_api_read_message
    */
  def readMessage(fullnames: Seq[String]) =
    ReadMessageBP.instantiate(
        input = RequestInput(("id" -> fullnames.mkString(","))),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: POST /api/unread_message
    *
    * Mark messages as unread.
    *
    * Requires [[Scope.PrivateMessages]] access.
    *
    * @param fullnames A sequence of fullnames corresponding to messages to mark as unread.
    * @return a [[RequestStub]] that, when processed, yields [[RedditNothing]].
    * @see http://www.reddit.com/dev/api#POST_api_unread_message
    */
  def unreadMessage(fullnames: Seq[String]) =
    UnreadMessageBP.instantiate(
        input = RequestInput(("id" -> fullnames.mkString(","))),
        extractor = RedditNothing)

  /**
    * Accesses the endpoint: GET /message/inbox
    *
    * Retrieve messages in the inbox.
    *
    * Requires [[Scope.PrivateMessages]] access.
    *
    * @param mark true/false
    * @param listOptions list options
    * @return a [[RequestStub]] that, when processed, yields a [[Listing[Message]]].
    * @see http://www.reddit.com/dev/api#GET_message_inbox
    * @todo is a listing
    */
  def inbox(mark: Boolean, listOptions: ListOptions = ListOptions.Empty) =
    InboxBP.instantiate(
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(Message))

  /**
    * Accesses the endpoint: GET /message/unread
    *
    * Retrieve unread messages.
    *
    * Requires [[Scope.PrivateMessages]] access.
    *
    * @param mark true/false
    * @param listOptions list options
    * @return a [[RequestStub]] that, when processed, yields [[Listing[Message]]].
    * @see http://www.reddit.com/dev/api#GET_message_inbox
    */
  def unread(mark: Boolean, listOptions: ListOptions = ListOptions.Empty) =
    UnreadBP.instantiate(
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(Message))

  /**
    * Accesses the endpoint: GET /message/sent
    *
    * Retrieve sent messages.
    *
    * Requires [[Scope.PrivateMessages]] access.
    *
    * @param mark true/false
    * @param listOptions list options
    * @return a [[RequestStub]] that, when processed, yields [[Listing[Message]]].
    * @see http://www.reddit.com/dev/api#GET_message_inbox
    */
  def sent(mark: Boolean, listOptions: ListOptions = ListOptions.Empty) =
    SentBP.instantiate(
        input = RequestInput(listOptions.toMap),
        extractor = Listing.extractor(Message))
}
