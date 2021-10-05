package ua.honchar.reddit.core.util.extensions

import ua.honchar.reddit.domain.model.PostData
import ua.honchar.reddit.domain.model.PostModelView

fun PostData.toModelView(): PostModelView {
    return PostModelView(
        id = id.orEmpty(),
        name = name.orEmpty(),
        title = title.orEmpty(),
        author = author.orEmpty(),
        thumbnail = thumbnail.orEmpty(),
        numComments = numComments.orZero(),
        created = created.orZero(),
        ratio = ratio.orZero(),
        ups = ups.orZero(),
        permalink = permalink.orEmpty()
    )
}

