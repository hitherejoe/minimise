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
    
    public func signUp(emailAddress: String, password: String, completion: @escaping (Bool) -> Void) {
        Auth.auth().createUser(withEmail: emailAddress, password: password) { authResult, error in
            if (authResult?.user != nil) {
                completion(true)
            } else {
                completion(false)
            }
        }
    }
    
    public func signIn(emailAddress: String, password: String, completion: @escaping (Bool) -> Void) {
        Auth.auth().signIn(withEmail: emailAddress, password: password) { authResult, error in
            if (authResult?.user != nil) {
                completion(true)
            } else {
                completion(false)
            }
        }
    }
    
    public func getDocuments(completion: @escaping (Array<Belonging>) -> Void) {
        var id = Auth.auth().currentUser?.uid
        print(id)
        Firestore.firestore().collection("items")
            .whereField("userId", isEqualTo: Auth.auth().currentUser?.uid)
        .addSnapshotListener { (querySnapshot, err) in
            if let err = err {
                print("Error getting documents: \(err)")
            } else {
                if let querySnapshot = querySnapshot {
                  let tasks = querySnapshot.documents.compactMap { document -> Belonging? in // (3)
                    try? document.data(as: Belonging.self) // (4)
                  }
                    completion(tasks)
                }
                
            }
        }
    }
}
