//
//  DashboardView.swift
//  Authentication
//
//  Created by Joe Birch on 10/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import Foundation
import SwiftUI

public struct DashboardView: View {
    
    public init() {
    }
    
    public var body: some View {
           return ZStack {
            Rectangle().foregroundColor(Color("primary"))
            .edgesIgnoringSafeArea(.all)
        }
    }

}
