//
//  ViewProvider.swift
//  Common
//
//  Created by Joe Birch on 29/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import Foundation
import SwiftUI

public protocol ScreenBuilder {
    func makeDashboardView() -> AnyView
    
    func makeAuthenticationView() -> AnyView
    
    func makeCreationView() -> AnyView
}
