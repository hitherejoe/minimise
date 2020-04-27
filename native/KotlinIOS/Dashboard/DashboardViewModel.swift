//
//  DashboardViewModel.swift
//  Dashboard
//
//  Created by Joe Birch on 23/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import Foundation
import SwiftUI
import Backend

class DashboardViewModel: ObservableObject {
    
    private var backendService: BackendProvider
    
    init(backendService: BackendProvider) {
        self.backendService = backendService
        self.backendService.getDocuments()
    }
}
