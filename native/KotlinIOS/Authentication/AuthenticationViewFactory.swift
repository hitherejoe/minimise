//
//  AuthenticationViewFactory.swift
//  Authentication
//
//  Created by Joe Birch on 26/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import SwiftUI
import Backend
import Common

public struct AuthenticationViewFactory : ViewFactory {

    let backendProvider: BackendProvider
    let viewProvider: ScreenBuilder
    
    public init(backendProvider: BackendProvider,
                viewProvider: ScreenBuilder) {
        self.backendProvider = backendProvider
        self.viewProvider = viewProvider
    }

    public func make() -> AnyView {
        let viewModel = AuthenticationViewModel(backendService: backendProvider)
        return AnyView(AuthenticationView(viewModel: viewModel, viewProvider: viewProvider))
    }
}
