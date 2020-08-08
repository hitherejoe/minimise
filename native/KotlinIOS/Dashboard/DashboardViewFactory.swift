//
//  DashboardViewFactory.swift
//  Dashboard
//
//  Created by Joe Birch on 26/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import Foundation
import SwiftUI
import Common

public struct DashboardViewFactory : ViewFactory {

    let viewProvider: ScreenFactory
    
    public init(viewProvider: ScreenFactory) {
        self.viewProvider = viewProvider
    }

    public func make() -> AnyView {
        let viewModel = DashboardViewModel()
        return AnyView(DashboardView(viewModel: viewModel, viewProvider: viewProvider))
    }
}
