package models;

import java.util.ArrayList;
import java.util.List;

public class NewsFeed {
    private List<News> newsList;

    // Constructor
    public NewsFeed() {
        newsList = new ArrayList<>();
    }

    // Method untuk menambah berita ke dalam feed
    public void addNews(News news) {
        newsList.add(news);
    }

    // Method untuk menampilkan semua berita yang ada
    public void displayNews() {
        for (News news : newsList) {
            System.out.println(news);
            System.out.println("-------------------------------");
        }
    }

    // Method untuk menampilkan berita berdasarkan ID
    public News getNewsById(int id) {
        for (News news : newsList) {
            if (news.getNewsId() == id) {
                return news;
            }
        }
        return null; // Jika berita dengan ID tidak ditemukan
    }

    // Method untuk mendapatkan berita berdasarkan judul
    public News getNewsByTitle(String title) {
        for (News news : newsList) {
            if (news.getTitle().equalsIgnoreCase(title)) {
                return news;
            }
        }
        return null; // Jika berita dengan judul tidak ditemukan
    }

    void displayNewsFeed() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
