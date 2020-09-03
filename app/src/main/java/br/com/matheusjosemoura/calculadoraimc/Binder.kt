package br.com.matheusjosemoura.calculadoraimc

import android.text.TextWatcher


class Binder {
    var heightWatcher: TextWatcher = MaskWatcher("#.##")
    var weightWatcher: TextWatcher = MaskWatcher("###.#")
}