package com.dixitpatel.quidcodemo.network

/**
 *  Authentication status
 */
sealed class AuthStatus
{
        object SUCCESS : AuthStatus()
        object ERROR : AuthStatus()
        object LOADING : AuthStatus()
}