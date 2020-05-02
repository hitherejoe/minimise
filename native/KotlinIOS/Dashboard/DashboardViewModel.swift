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
    
    @Published internal var state: Array<Belonging> = [Belonging]()
    
    private var backendService: BackendProvider
    
    init(backendService: BackendProvider) {
        self.backendService = backendService
        backendService.getDocuments() { (belongings) in
            self.handleBelongingsLoaded(belongings: belongings)
        }
    }
    
    func setSelectedIndex(index: Int) {
        backendService.getDocuments() { (belongings) in
            self.handleBelongingsLoaded(belongings: belongings)
        }
    }
    
    private func handleBelongingsLoaded(belongings: Array<Belonging>) {
        state = belongings
    }

}
