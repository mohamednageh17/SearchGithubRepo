package com.example.searchgithubrepo.data.mapper

import com.example.searchgithubrepo.data.model.GithubRepoResponse
import com.example.searchgithubrepo.data.model.License
import com.example.searchgithubrepo.data.model.Owner
import com.example.searchgithubrepo.domain.model.LicenseModel
import com.example.searchgithubrepo.domain.model.OwnerModel
import com.example.searchgithubrepo.domain.model.RepoModel

fun GithubRepoResponse.mapToDomain() = this.items!!.map {
    RepoModel(
        id = it.id,
        nodeID = it.nodeID,
        name = it.name,
        fullName = it.fullName,
        owner = it.owner!!.mapToDomain(),
        private = it.private,
        htmlURL = it.htmlURL,
        description = it.description,
        fork = it.fork,
        url = it.url,
        createdAt = it.createdAt,
        updatedAt = it.updatedAt,
        pushedAt = it.pushedAt,
        homepage = it.homepage,
        size = it.size,
        stargazersCount = it.stargazersCount,
        watchersCount = it.watchersCount,
        language = it.language,
        forksCount = it.forksCount,
        openIssuesCount = it.openIssuesCount,
        masterBranch = it.masterBranch,
        defaultBranch = it.defaultBranch,
        score = it.score,
        archiveURL = it.archiveURL,
        assigneesURL = it.assigneesURL,
        blobsURL = it.blobsURL,
        branchesURL = it.branchesURL,
        collaboratorsURL = it.collaboratorsURL,
        commentsURL = it.commentsURL,
        commitsURL = it.commitsURL,
        compareURL = it.compareURL,
        contentsURL = it.contentsURL,
        contributorsURL = it.contributorsURL,
        deploymentsURL = it.deploymentsURL,
        downloadsURL = it.downloadsURL,
        eventsURL = it.eventsURL,
        forksURL = it.forksURL,
        gitCommitsURL = it.gitCommitsURL,
        gitRefsURL = it.gitRefsURL,
        gitTagsURL = it.gitTagsURL,
        gitURL = it.gitURL,
        issueCommentURL = it.issueCommentURL,
        issueEventsURL = it.issueEventsURL,
        issuesURL = it.issuesURL,
        keysURL = it.keysURL,
        labelsURL = it.labelsURL,
        languagesURL = it.languagesURL,
        mergesURL = it.mergesURL,
        milestonesURL = it.milestonesURL,
        notificationsURL = it.notificationsURL,
        pullsURL = it.pullsURL,
        releasesURL = it.releasesURL,
        sshURL = it.sshURL,
        stargazersURL = it.stargazersURL,
        statusesURL = it.statusesURL,
        subscribersURL = it.subscribersURL,
        subscriptionURL = it.subscriptionURL,
        tagsURL = it.tagsURL,
        teamsURL = it.teamsURL,
        treesURL = it.treesURL,
        cloneURL = it.cloneURL,
        mirrorURL = it.mirrorURL,
        hooksURL = it.hooksURL,
        svnURL = it.svnURL,
        forks = it.forks,
        openIssues = it.openIssues,
        watchers = it.watchers,
        hasIssues = it.hasIssues,
        hasProjects = it.hasProjects,
        hasPages = it.hasPages,
        hasWiki = it.hasWiki,
        hasDownloads = it.hasDownloads,
        archived = it.archived,
        disabled = it.disabled,
        visibility = it.visibility,
        license = it.license?.mapToDomain()
    )
}

fun Owner.mapToDomain(): OwnerModel {
    return OwnerModel(
        login = this.login,
        id = this.id,
        nodeID = this.nodeID,
        avatarURL = this.avatarURL,
        gravatarID = this.gravatarID,
        url = this.url,
        receivedEventsURL = this.receivedEventsURL,
        type = this.type,
        htmlURL = this.htmlURL,
        followersURL = this.followersURL,
        followingURL = this.followingURL,
        gistsURL = this.gistsURL,
        starredURL = this.starredURL,
        subscriptionsURL = this.subscriptionsURL,
        organizationsURL = this.organizationsURL,
        reposURL = this.reposURL,
        eventsURL = this.eventsURL,
        siteAdmin = this.siteAdmin
    )
}

fun License.mapToDomain(): LicenseModel {
    return LicenseModel(
        key = this.key,
        name = this.name,
        url = this.url,
        spdxID = this.spdxID,
        nodeID = this.nodeID,
        htmlURL = this.htmlURL,
    )
}