//
//  DashboardViewFactory.swift
//  Dashboard
//
//  Created by Joe Birch on 26/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import Foundation
import SwiftUI
import Backend
import Common

public struct DashboardViewFactory : ViewFactory {

    let backendProvider: BackendProvider
    let viewProvider: ScreenFactory
    
    public init(backendProvider: BackendProvider,
                viewProvider: ScreenFactory) {
        self.backendProvider = backendProvider
        self.viewProvider = viewProvider
    }

    public func make() -> AnyView {
        let viewModel = DashboardViewModel(backendService: backendProvider)
        return AnyView(DashboardView(viewModel: viewModel, viewProvider: viewProvider))
    }
}
