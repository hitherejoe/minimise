//
//  BackendProvider.swift
//  BackendProvider
//
//  Created by Joe Birch on 25/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import Foundation
import Firebase

public class BackendProvider {
    
    public init() { }
    
    public func configure() {
        FirebaseApp.configure()
    }
    
    public func currentUser() -> User! {
        return Auth.auth().currentUser
    }
    
    public func getDocuments() {
        Firestore.firestore().collection("belongings")
            .whereField("userId", isEqualTo: Auth.auth().currentUser?.uid)
        .addSnapshotListener { (querySnapshot, err) in
            if let err = err {
                print("Error getting documents: \(err)")
            } else {
                for document in querySnapshot!.documents {
                    print("\(document.documentID) => \(document.data())")
                }
            }
        }
    }
}
