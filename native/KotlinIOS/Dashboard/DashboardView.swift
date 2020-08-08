//
//  DashboardView.swift
//  Authentication
//
//  Created by Joe Birch on 10/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//
import Foundation
import SwiftUI
import Common

public struct DashboardView: View {
    
    @ObservedObject var viewModel: DashboardViewModel
    @State private var selectorIndex = 0
    @State private var searchText : String = ""
    @State private var action: Int? = 0
    var viewProvider: ScreenFactory
    
    public var body: some View {
        return NavigationView {
            ZStack {
                Rectangle().foregroundColor(Color("primary"))
                .edgesIgnoringSafeArea(.all)
                
                ZStack(alignment: .bottomTrailing) {
                    VStack (alignment: .leading){
                        SearchBar(text: $searchText)
                        Picker("Numbers", selection: $selectorIndex.onChange(colorChange)) {
                            Text("Pending").tag(0)
                            Text("Owned").tag(1)
                        }.pickerStyle(SegmentedPickerStyle())
                            .padding(.leading, 8)
                        .padding(.trailing, 8)
                        /*
                        List {
                            ForEach (self.viewModel.state) { task in // (3)
                                ProductCard(title: task.name, description: task.storeName,
                                    date: task.pendingDate!.dateValue()) {
                                    
                                } // (6)
                            }
                        }.onAppear { UITableView.appearance().separatorStyle = .none } .onDisappear { UITableView.appearance().separatorStyle = .singleLine }
                            .padding(.top, 6)
 */
                    }
                    NavigationLink(destination: viewProvider.makeCreationView(), tag: 1, selection: $action) {
                        Button(action: {
                            self.action = 1
                        }) {
                            Image(systemName: "plus")
                            .foregroundColor(.white)
                                .font(.system(size: 24, weight: .regular))
                                .padding(.all, 2)
                                
                        }.buttonStyle(BlueCircleButtonStyle())
                        .padding(.bottom, 16)
                        .padding(.trailing, 16)
                    }
                }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
            }.navigationBarTitle("")
            .navigationBarHidden(true)
        }
    }
    
    func colorChange(_ tag: Int) {
        self.viewModel.setSelectedIndex(index: tag)
    }

    struct TaskCell: View { // (5)
      
      var body: some View {
        HStack {// (12)
          Text("task.name")
        }
      }
    }
    
    struct BlueCircleButtonStyle: ButtonStyle {
        func makeBody(configuration: Self.Configuration) -> some View {
            configuration.label.padding().modifier(MakeSquareBounds()).background(Circle().fill(Color.blue))

        }
    }

    struct MakeSquareBounds: ViewModifier {

        @State var size: CGFloat = 1000
        func body(content: Content) -> some View {
            let c = ZStack {
                content.alignmentGuide(HorizontalAlignment.center) { (vd) -> CGFloat in
                    DispatchQueue.main.async {
                        self.size = max(vd.height, vd.width)
                    }
                    return vd[HorizontalAlignment.center]
                }
            }
            return c.frame(width: size, height: size)
        }
    }
    
    struct ProductCard: View {
        
        
        var title:String    // Product Title
        var description:String // Product Description
        var date:String // Product Description
        var buttonHandler: (()->())?
        
        init(title:String, description:String, date:Date, buttonHandler: (()->())?) {
            let formatter3 = DateFormatter()
            formatter3.dateFormat = "d MMM y"
            
            self.title = title
            self.description = description
            self.date = "Added " + date.timeAgo(numericDates: true)
            self.buttonHandler = buttonHandler
        }
        
        var body: some View {
            VStack(alignment: .leading, spacing: 0) {
                    Text(self.title)
                        .fontWeight(Font.Weight.heavy)
                    Text(self.description)
                        .font(Font.custom("HelveticaNeue-Bold", size: 16))
                        .foregroundColor(Color.gray)
                Spacer()
                Text(self.date)
                .foregroundColor(Color.gray)
                .font(Font.custom("HelveticaNeue", size: 14))
                }.padding(16).onTapGesture {
                    self.buttonHandler?()
            }
            .frame(minWidth: 0, maxWidth: .infinity, minHeight: 120, maxHeight: .infinity, alignment: .topLeading)
            .background(Color.white)
            .cornerRadius(6)
            .shadow(color: Color.black.opacity(0.2), radius: 2, x: 0, y: 1)
        }
    }
}

struct SearchBar: UIViewRepresentable {

    @Binding var text: String

    class Coordinator: NSObject, UISearchBarDelegate {

        @Binding var text: String

        init(text: Binding<String>) {
            _text = text
        }

        func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
            text = searchText
        }
    }

    func makeCoordinator() -> SearchBar.Coordinator {
        return Coordinator(text: $text)
    }

    func makeUIView(context: UIViewRepresentableContext<SearchBar>) -> UISearchBar {
        let searchBar = UISearchBar(frame: .zero)
        searchBar.delegate = context.coordinator
        searchBar.searchBarStyle = .minimal
        return searchBar
    }

    func updateUIView(_ uiView: UISearchBar, context: UIViewRepresentableContext<SearchBar>) {
        uiView.text = text
    }
}

extension Binding {
    func onChange(_ handler: @escaping (Value) -> Void) -> Binding<Value> {
        return Binding(
            get: { self.wrappedValue },
            set: { selection in
                self.wrappedValue = selection
                handler(selection)
        })
    }
}
