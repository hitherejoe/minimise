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
    
    @ObservedObject var viewModel: DashboardViewModel
    @State private var selectorIndex = 0

    public var body: some View {
           return ZStack {
            Rectangle().foregroundColor(Color("primary"))
            .edgesIgnoringSafeArea(.all)
            
            VStack (alignment: .leading){
                Picker("Numbers", selection: $selectorIndex) {
                    Text("Pending").tag(0)
                    Text("Owned").tag(1)
                }.pickerStyle(SegmentedPickerStyle())
            }
                
        
        }
    }

}
