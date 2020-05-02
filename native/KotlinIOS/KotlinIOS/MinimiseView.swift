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

    var viewProvider: ViewProvider
    var authenticated: Bool
    
    init(authenticated: Bool, viewProvider: ViewProvider) {
        self.authenticated = authenticated
        self.viewProvider = viewProvider
    }

    var body: some View {
        if self.authenticated {
            return viewProvider.makeDashboardView()
        } else {
            return viewProvider.makeAuthenticationView()
        }
    }
}
