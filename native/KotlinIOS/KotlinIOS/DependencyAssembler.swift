//
//  Assembler.swift
//  KotlinIOS
//
//  Created by Joe Birch on 26/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import UIKit
import SwiftUI
import Authentication
import Firebase
import Backend
import Dashboard
import Swinject

class DependencyAssembler: Assembly {
    
    func assemble(container: Container) {
        container.register(BackendProvider.self) { _ in BackendProvider() }
        .inObjectScope(.container)
        .initCompleted { resolver, screen in
            (resolver.resolve(BackendProvider.self)!).configure()
        }
    
        container.register(DashboardViewFactory.self) { resolver -> DashboardViewFactory in
                     DashboardViewFactory(
                         backendProvider: container.resolve(BackendProvider.self)!
                     )
                 }
                 
                 container.register(AuthenticationViewFactory.self) { resolver -> AuthenticationViewFactory in
                     AuthenticationViewFactory(
                         backendProvider: container.resolve(BackendProvider.self)!
                     )
                 }
    
    }
}
