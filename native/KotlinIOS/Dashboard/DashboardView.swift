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
            
            ZStack(alignment: .bottomTrailing) {
                VStack (alignment: .leading){
                    NavigationView {
                        Text("Minimise")
                    }
                    Picker("Numbers", selection: $selectorIndex) {
                        Text("Pending").tag(0)
                        Text("Owned").tag(1)
                    }.pickerStyle(SegmentedPickerStyle()).frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
                }
                Button(action: {
                    // your action here
                }) {
                    Text("Add item")
                        .padding(.bottom, 16)
                        .padding(.trailing, 16)
                }
            }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
        }
    }

}
