//
//  AuthenticationViewFactory.swift
//  Authentication
//
//  Created by Joe Birch on 26/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import SwiftUI
import SharedAuthentication
import Common

public struct AuthenticationViewFactory : ViewFactory {

    let backendProvider: Authenticate
    let viewProvider: ScreenFactory
    
    public init(authenticate: Authenticate,
                viewProvider: ScreenFactory) {
        self.backendProvider = authenticate
        self.viewProvider = viewProvider
    }

    public func make() -> AnyView {
        let viewModel = AuthenticationViewModel(authenticate: backendProvider)
        return AnyView(AuthenticationView(viewModel: viewModel, viewProvider: viewProvider))
    }
}
