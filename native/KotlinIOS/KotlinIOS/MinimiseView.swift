//
//  MinimiseView.swift
//  KotlinIOS
//
//  Created by Joe Birch on 26/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import Foundation
import SwiftUI
import Dashboard
import Authentication

struct MinimiseView: View {

    @EnvironmentObject
    var viewBuilder: ViewBuilder
    var authenticated: Bool
    
    init(authenticated: Bool) {
        self.authenticated = authenticated
    }

    var body: some View {
        if self.authenticated {
            return viewBuilder.makeDashboardView()
        } else {
            return viewBuilder.makeAuthenticationView()
        }
    }
}
